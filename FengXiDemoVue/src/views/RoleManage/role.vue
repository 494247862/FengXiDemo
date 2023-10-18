<template>
  <div class="app-container" style="height:100%">
    <el-row>
      <el-card style="height: 100%">
        <el-form label-width="80px" label-position="right" class="search-form" :inline="true" :model="queryData">
          <el-form-item label="角色编号:" prop="roleCode">
            <el-input v-model="queryData.roleCode"/>
          </el-form-item>
          <el-form-item label="角色名称:" prop="roleName">
            <el-input v-model="queryData.roleName"/>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="()=>{queryData={}}">清空</el-button>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="QueryRolePage(currentPage,pageSize)">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>
    <el-row style="height: calc(100% - 110px)">
      <el-card style="height: 100%" body-style="height:100%">
        <div slot="header" style="width: 100%;
        display: flex;align-items: center;
          justify-content: space-between;">
          <div>
            <i class="el-icon-s-grid"></i>
            <span style="margin-left: 5px;">角色数据</span>
          </div>
          <div>
            <el-button @click="handleAddRole" size="small" type="primary" v-if="$authButton('add')">新增角色</el-button>
          </div>
        </div>

        <!--        表格数据-->
        <el-table :data="rolesList" v-loading="loading" style="width: 100%;height: calc(100% - 120px);overflow-y: auto"
                  border>
          <el-table-column align="center" label="角色编号" width="220">
            <template slot-scope="scope">
              {{ scope.row.roleCode }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="角色名称" width="220">
            <template slot-scope="scope">
              {{ scope.row.roleName }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="描述">
            <template slot-scope="scope">
              {{ scope.row.description }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="handleEdit(scope)" v-if="$authButton('update')">
                编辑
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope)" v-if="$authButton('delete')">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--        分页-->
        <div style="width: 100%;text-align: center;margin-top: 20px;">
          <el-pagination layout="total, sizes, prev, pager, next, jumper"
                         background
                         @current-change="handleCurrentChange"
                         @size-change="sizeChange"
                         :current-page="currentPage"
                         :page-sizes="[10, 20, 30, 50]"
                         :total="total"
                         :page-size="pageSize"/>
        </div>
      </el-card>
    </el-row>

    <el-drawer
      :visible.sync="dialogVisible"
      direction="rtl"
      :title="dialogType==='edit'?'编辑角色':'新增角色'">
      <div style="margin-left: 30px;margin-right: 30px;height: 100%;display: flex;
          flex-direction: column;
           justify-content: space-between;">
        <el-form :model="role" label-width="80px" label-position="left">
          <el-form-item label="角色编号">
            <el-input v-model="role.roleCode" :readonly="dialogType==='edit'" placeholder="请输入角色编号"/>
          </el-form-item>
          <el-form-item label="角色名称">
            <el-input v-model="role.roleName" placeholder="请输入角色名称"/>
          </el-form-item>
          <el-form-item label="描述">
            <el-input
              v-model="role.description"
              :autosize="{ minRows: 2, maxRows: 4}"
              type="textarea"
              placeholder="请输入角色描述"
            />
          </el-form-item>

          <div style="display: flex">
            <el-form-item label="菜单" style="width: 50%">
              <el-tree ref="tree" :check-strictly="checkStrictly" :data="menusData" :props="defaultProps" show-checkbox
                       node-key="id" class="permission-tree"/>
            </el-form-item>

            <el-form-item label="按钮" style="width: 50%">
              <el-tree ref="buttonTree" :check-strictly="false" :data="buttonData" :props="buttonProps" show-checkbox
                       node-key="id"/>
            </el-form-item>
          </div>

        </el-form>
        <div style="text-align:right;margin-bottom: 20px;">
          <el-button type="danger" @click="dialogVisible=false">
            取消
          </el-button>
          <el-button type="primary" @click="confirmRole">
            确认
          </el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
  import path from 'path'
  import {getAllRoles, addRole, deleteRole, updateRole, QueryRolePage} from '@/api/role'
  import {getMenuTrees, getMenuTreesByRoleId} from '@/api/menu'
  import {getMenuButtonTree} from '@/api/button'

  const defaultRole = {
    id: '',
    roleCode: '',
    roleName: '',
    description: '',
    roleMenus: []
  }

  export default {
    name: 'RoleManage',
    data() {
      return {
        role: Object.assign({}, defaultRole),
        menusData: [],
        buttonData: [],
        rolesList: [],
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'children',
          label: 'menuName'
        },
        buttonProps: {
          children: 'children',
          label: 'name'
        },
        queryData: {},

        currentPage: 1,
        pageSize: 10,
        total: 10,
        loading: false
      }
    },
    created() {
      // Mock: get all routes and roles list from server
      this.initData()
    },
    methods: {
      initData() {
        this.getMenuButtonTree()
        this.getMenuTrees()
        this.QueryRolePage(this.currentPage, this.pageSize)
      },

      // 查询数据
      QueryRolePage(page, pageSize) {
        let data = {...this.queryData}
        data.page = page
        data.pageSize = pageSize
        this.loading = true
        QueryRolePage(data).then(res => {
          this.rolesList = res.data
          this.total = res.count
          this.loading = false
        }).catch(err => {
          this.$notify.error({
            title: '查询失败',
            message: err.message
          })
          this.loading = false
        })
      },

      handleCurrentChange(val) {
        this.currentPage = val
        this.QueryRolePage(val, this.pageSize)
      },

      sizeChange(val) {
        this.pageSize = val
        this.QueryRolePage(this.currentPage, val)
      },

      async getMenuTrees() {
        const menus = await getMenuTrees()
        this.menusData = menus
      },

      async getMenuButtonTree() {
        const buttonData = await getMenuButtonTree()
        this.buttonData = buttonData
      },

      async getAllRoles() {
        const roles = await getAllRoles()
        this.rolesList = roles
      },

      getCheckedMenus(menuIds, menus) {
        let data = []
        menus.forEach(menu => {
          if (menuIds && menuIds.includes(menu.id)) {
            data.push(menu)
            if (menu.children) {
              const temp = this.getCheckedMenus(menuIds, menu.children)
              if (temp.length > 0) {
                data = [...data, ...temp]
              }
            }
          }
        })
        return data
      },
      getRoleMenus(roleID) {
        const roleMenus = getMenuTreesByRoleId(roleID)
        return roleMenus
      },
      handleAddRole() {
        this.role = Object.assign({}, defaultRole)
        if (this.$refs.tree) {
          this.$refs.tree.setCheckedNodes([])
          this.$refs.buttonTree.setCheckedNodes([])
        }
        this.dialogType = 'new'
        this.dialogVisible = true
      },
      handleEdit(scope) {
        this.dialogType = 'edit'
        this.dialogVisible = true
        this.checkStrictly = true
        this.role = scope.row
        this.$nextTick(() => {
          // const roleMenus = this.getCheckedMenus(this.role.menuIds, this.menusData)
          // this.$refs.tree.setCheckedNodes(roleMenus)
          // this.$refs.tree.setCheckedKeys(role.menuIds)
          // this.$refs.buttonTree.setCheckedKeys([1,2])

          // set checked state of a node not affects its father and child nodes
          let menuIds = this.role.menuIds === null ? [] : this.role.menuIds.split(',').map(Number)
          let buttonIds = this.role.buttonIds === null ? [] : this.role.buttonIds.split(',').map(Number)
          this.$refs.tree.setCheckedKeys(menuIds)
          this.$refs.buttonTree.setCheckedKeys(buttonIds)
          this.checkStrictly = false
        })
      },
      handleDelete({$index, row}) {
        this.$confirm('确定要删除此角色吗？', 'Warning', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            await deleteRole(row.id)
            this.rolesList.splice($index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          .catch(err => {
            console.error(err)
          })
      },
      async confirmRole() {
        const isEdit = this.dialogType === 'edit'

        // 获取选中的子节点
        let checkedKeys = this.$refs.tree.getCheckedKeys()
        // 获取选中的父节点
        let hafCheckedKeys = this.$refs.tree.getHalfCheckedKeys()
        // 合并
        let functionIdList = checkedKeys.concat(hafCheckedKeys)

        // 按钮树
        let buttonCheckedKeys = this.$refs.buttonTree.getCheckedKeys()
        buttonCheckedKeys = buttonCheckedKeys.filter(t => typeof (t) === 'number')

        this.role.menuIds = functionIdList
        this.role.buttonIds = buttonCheckedKeys
        if (isEdit) {
          await updateRole(this.role)
          for (let index = 0; index < this.rolesList.length; index++) {
            if (this.rolesList[index].id === this.role.id) {
              this.rolesList.splice(index, 1, Object.assign({}, this.role))
              break
            }
          }
        } else {
          await addRole(this.role)
          await this.getAllRoles()
        }

        const {roleCode, roleName} = this.role
        this.dialogVisible = false
        this.$notify({
          title: '操作成功',
          dangerouslyUseHTMLString: true,
          message: `
            <div>用户编号: ${roleCode}</div>
            <div>用户名称: ${roleName}</div>
          `,
          type: 'success'
        })
        this.initData()
      }
    },
  }
</script>

<style lang="scss" scoped>
  .app-container {
    .roles-table {
      margin-top: 30px;
    }

    .permission-tree {
      margin-bottom: 30px;
    }
  }

  .el-row {
    margin-bottom: 20px;
    margin-left: 10px;
    margin-right: 10px;
  }

  .el-row:last-child {
    margin-bottom: 0
  }

  .search-form .el-form-item {
    margin-bottom: 0
  }
</style>
