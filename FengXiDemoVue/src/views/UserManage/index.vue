<template>
  <div class="app-container">
    <el-row>
      <!--      查询条件按-->
      <el-card style="height: 100%">
        <el-form
          class="QueryForm"
          label-width="80px"
          label-position="right"
          :inline="true"
          :model="query"
        >
          <el-form-item label="账号:">
            <el-input v-model="query.account"/>
          </el-form-item>
          <el-form-item label="昵称:">
            <el-input v-model="query.nickName"/>
          </el-form-item>
          <el-form-item label="联系方式:">
            <el-input v-model="query.phone"/>
          </el-form-item>
          <el-form-item label="所属角色:">
            <el-select
              v-model="queryRoles"
              multiple
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(role, index) in rolesList"
                :key="index"
                :label="role.roleName"
                :value="role.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="clearQueryData()">清空</el-button>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="queryTableData()">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>

    <el-row style="margin-top: 10px;height: calc(100% - 100px)">
      <!--      查询条件按-->
      <el-card style="height: 100%" body-style="height:100%">
        <div slot="header" style="width: 100%;
        display: flex;align-items: center;
          justify-content: space-between;">
          <div>
            <i class="el-icon-s-grid"></i>
            <span style="margin-left: 5px;">用户数据</span>
          </div>
          <div>
            <el-button @click="handleAddUser" size="small" type="primary" v-if="$authButton('addUser')"> 新增用户
            </el-button>
          </div>
        </div>

        <el-table
          :style="{height:'calc(100% - 115px)','overflow-y': 'auto'}"
          :cell-style="{'text-align': 'center'}"
          :header-cell-style="{'text-align': 'center'}"
          :data="usersList"
          border
        >
          <el-table-column align="center" label="账号" width="220">
            <template slot-scope="scope">
              {{ scope.row.account }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="昵称" width="220">
            <template slot-scope="scope">
              {{ scope.row.nickName }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="角色">
            <template slot-scope="scope">
              <el-tag v-for="(item,index) in scope.row.roleList" style="margin-left: 10px;" :key="index">{{item}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="手机号">
            <template slot-scope="scope">
              {{ scope.row.phone }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="性别">
            <template slot-scope="scope">
              {{ scope.row.sex }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleEdit(scope)" v-if="$authButton('update')">
                编辑
              </el-button>
              <el-button style="color:orange" type="text" size="small" @click="handleReset(scope)"
                         v-if="$authButton('resetPassword')">
                重置密码
              </el-button>
              <el-button
                v-if="$authButton('delete')"
                style="color:red"
                type="text"
                size="small"
                @click="handleDelete(scope)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            background
            layout="total, prev, pager, next, jumper"
            :total="pageCount"
            :current-page="currentPage"
            @current-change="pageChange"
            :page-sizes="[10, 20, 30, 50]"
            @size-change="sizeChange"
          >
          </el-pagination>
        </div>
      </el-card>
    </el-row>

    <el-dialog
      width="500px"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible"
      :title="dialogType === 'edit' ? '编辑用户' : '新增用户'"
    >
      <el-form :model="user" label-width="80px" ref="formRef" label-position="right">
        <el-form-item label="账号" prop="account" :rules="[{ required: true, message: '账号不能为空'}]">
          <el-input
            v-model="user.account"
            :readonly="dialogType === 'edit'"
            placeholder="请输入用户账号"
          />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="user.nickName" placeholder="请输入用户昵称"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="user.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="user.sex" size="small">
            <el-radio-button label="男"/>
            <el-radio-button label="女"/>
          </el-radio-group>
        </el-form-item>
        <el-tooltip
          v-model="capsTooltip"
          content="大写锁定"
          placement="right"
          manual
        >
          <el-form-item label="密码" prop="password"
                        :rules="[{ required: dialogType!=='edit', message: '密码不能为空'}]">
            <el-input
              class="pwd-input"
              readOnly
              onfocus="this.removeAttribute('readonly');"
              onblur="this.setAttribute('readonly',true);"
              autocomplete="on"
              :key="passwordType"
              ref="password"
              v-model="user.password"
              :type="passwordType"
              placeholder="密码"
              name="password"
              tabindex="4"
              @keyup.native="checkCapslock"
              @blur="capsTooltip = false"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon
                :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
              />
            </span>
          </el-form-item>
        </el-tooltip>

        <el-form-item label="选择角色">
          <el-select
            v-model="checkRoles"
            multiple
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="(role, index) in rolesList"
              :key="index"
              :label="role.roleName"
              :value="role.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="部门">
          <el-cascader
            v-model="departmentIds"
            style="width: 100%"
            clearable
            :props="{ checkStrictly: true ,emitPath: false,multiple: true}"
            :options="departmentOptions">

          </el-cascader>
        </el-form-item>
      </el-form>
      <div style="text-align: right">
        <el-button type="danger" @click="()=>{this.dialogVisible = false;this.user = {}}">
          取消
        </el-button>
        <el-button type="primary" @click="confirmUser"> 确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    addUser,
    updateUser,
    deleteUser,
    getAllUser,
    queryUserData
  } from '@/api/user'
  import {getAllRoles} from '@/api/role'
  import {getDepartmentTree} from '@/api/department'
  import {timingSafeEqual} from 'crypto'

  const defaultUser = {
    id: '',
    account: '',
    nickName: '',
    password: '',
    phone: '',
    sex: '',
    roleIds: ''
  }

  const defaultQuery = {
    account: '',
    nickName: '',
    phone: '',
    sex: '',
    roleIds: [],
    page: 1,
    pageSize: 10
  }

  export default {
    name: 'UserManage',
    data() {
      return {
        user: Object.assign({}, defaultUser),
        query: Object.assign({}, defaultQuery),
        menusData: [],
        usersList: [],
        rolesList: [],

        checkRoles: [],
        departmentIds: [],
        queryRoles: [],
        departmentOptions: [],

        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        passwordType: 'password',
        capsTooltip: false,

        pageCount: 1,
        currentPage: 1
      }
    },
    created() {
      this.getAllUser()
    },
    methods: {
      getDepartmentTree() {
        getDepartmentTree().then(res => {
          this.departmentOptions = this.diyDepartmentTree(res)
        }).catch(err => {
          this.$notify.error({
            title: '获取部门数据错误',
            message: err.message
          })
        })
      },
      // 递归处理部门树的数据
      diyDepartmentTree(list) {
        let res = []
        for (let i = 0; i < list.length; i++) {
          res.push(this.diyDepartmentTreeItem(list[i]))
        }
        return res
      },
      diyDepartmentTreeItem(data) {
        let children = []
        let res = {value: data.id, label: data.departmentName}
        if (data.children.length > 0) {
          for (let i = 0; i < data.children.length; i++) {
            children.push(this.diyDepartmentTreeItem(data.children[i]))
          }
        } else {
          children = null
        }
        res.children = children
        return res
      },
      sizeChange(val) {
        this.query.pageSize = val
      },
      //页面改变
      pageChange(val) {
        this.currentPage = val
        this.queryTableData(val)
      },
      // 清空
      clearQueryData() {
        this.queryRoles = []
        this.query = Object.assign({}, defaultQuery)
      },
      // 查询数据
      queryTableData(selectPage = 1) {
        this.query.roleIds = this.queryRoles
        this.query.page = selectPage
        this.currentPage = selectPage

        queryUserData(this.query)
          .then((res) => {
            this.usersList = res.data
            this.pageCount = res.count
          })
          .catch((err) => {
            this.$notify.error({
              title: '错误',
              message: err.message
            })
          })
      },
      checkCapslock(e) {
        const {key} = e
        this.capsTooltip = key && key.length === 1 && key >= 'A' && key <= 'Z'
      },
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },

      async getAllRoles() {
        const roles = await getAllRoles()
        this.rolesList = roles
      },

      getAllUser() {
        queryUserData(this.query)
          .then((res) => {
            this.usersList = res.data
            this.pageCount = res.count
          })
          .catch((err) => {
            this.$notify.error({
              title: '错误',
              message: err.message
            })
          })
      },

      getCheckedRoles(roleIds) {
        let data = []
        if (roleIds && roleIds.length > 0) {
          data = roleIds.split(',').map(Number)
        }
        return data
      },

      handleAddUser() {
        this.user = Object.assign({}, defaultUser)
        this.checkRoles = []
        this.departmentIds = []
        this.dialogType = 'new'
        this.dialogVisible = true
        this.getAllRoles()
        this.getDepartmentTree()
      },
      handleEdit(scope) {
        this.dialogType = 'edit'
        this.dialogVisible = true
        this.checkStrictly = true
        this.user = {...scope.row}
        this.user.password = ''
        this.checkRoles = this.getCheckedRoles(this.user.roleIds)
        this.departmentIds = this.getCheckedRoles(this.user.departmentIds)
        this.getAllRoles()
        this.getDepartmentTree()
      },
      async handleReset(scope) {
        this.user = {...scope.row}
        this.user.password = '12345'
        this.$confirm('确定要重置此用户密码吗？', 'Warning', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            await updateUser(this.user)
            this.$message({
              type: 'success',
              message: '重置成功!'
            })
          })
          .catch((err) => {
            console.log(err)
          })
        await this.getAllUser()
      },

      handleDelete({$index, row}) {
        this.$confirm('确定要删除此用户吗？', 'Warning', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            await deleteUser(row.id)
            this.usersList.splice($index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          .catch((err) => {
            console.error(err)
          })
      },
      async confirmUser() {
        this.$refs['formRef'].validate(async valid => {
          if (valid) {
            const isEdit = this.dialogType === 'edit'
            this.user.roleIds = this.checkRoles.join() === "" ? null : this.checkRoles.join()
            this.user.departmentIds = this.departmentIds.join() === "" ? null : this.departmentIds.join()
            if (isEdit) {
              await updateUser(this.user)
            } else {
              await addUser(this.user)
            }

            // await getAllUser()
            this.queryTableData(this.currentPage)

            const {account, nickName} = this.user
            this.dialogVisible = false
            this.$notify({
              title: '操作成功',
              dangerouslyUseHTMLString: true,
              message: `
              <div>账号: ${account}</div>
              <div>昵称: ${nickName}</div>
            `,
              type: 'success'
            })
          } else {
            return false
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  $dark_gray: #889aa4;

  .pagination {
    width: 100%;
    margin-top: 20px;
    display: inline-flex;
    justify-content: center;
  }

  .QueryForm .el-form-item {
    margin-bottom: 0;
  }

  .app-container {
    .users-table {
      margin-top: 30px;
    }

    .permission-tree {
      margin-bottom: 30px;
    }
  }

  .pwd-input {
    display: inline-block;
    height: 47px;

    input {
      background: transparent;
      border: 0px;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      height: 47px;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
</style>
