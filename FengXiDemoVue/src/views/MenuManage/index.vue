<!--菜单管理-->
<template>
  <div class="app-container" style="height:100%">
    <el-row>
      <!--      查询条件按-->
      <el-card style="height: 100%">
        <el-form label-width="80px" label-position="right" :inline="true" :model="QueryData">
          <el-form-item label="菜单名称:">
            <el-input v-model="QueryData.menuName"/>
          </el-form-item>
          <el-form-item label="菜单编号:">
            <el-input v-model="QueryData.menuCode"/>
          </el-form-item>
          <el-form-item label="菜单地址:">
            <el-input v-model="QueryData.menuUrl"/>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="clearQueryData">清空</el-button>
          </el-form-item>
          <el-form-item class="submit-button">
            <el-button @click="QueryTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>

    <el-row style="height: calc(100% - 100px)">
      <el-card style="height: 100%">
        <div slot="header" style="width: 100%;
        display: flex;align-items: center;
          justify-content: space-between;">
          <div>
            <i class="el-icon-s-grid"></i>
            <span style="margin-left: 5px;">菜单数据</span>
          </div>
          <div>
            <el-button @click="openAdd(null)" size="small" type="primary" v-if="$authButton('add')">新增</el-button>
          </div>
        </div>

        <!--    表格数据-->
        <el-table
          row-key="id"
          lazy
          :tree-props="{children:'children',hasChildren:true}"
          :data="TableData"
          :header-cell-style="{'text-align': 'center'}"
          :cell-style="{'text-align': 'center'}"
          style="width: 100%;overflow-y: auto"
          border>
          <el-table-column
            prop="menuName"
            label="菜单名称">
          </el-table-column>

          <el-table-column
            prop="menuCode"
            label="菜单编号">
          </el-table-column>

          <el-table-column
            prop="menuUrl"
            label="菜单地址">
          </el-table-column>

          <el-table-column
            prop="number"
            label="序号">
          </el-table-column>

          <el-table-column
            label="图标">
            <template slot-scope="scope">
              <div v-if="scope.row.icon">
                <i v-if="scope.row.icon.indexOf('el-icon')!==-1" :class="[scope.row.icon, 'sub-el-icon']"/>
                <svg-icon v-if="scope.row.icon.indexOf('el-icon')===-1" :icon-class="scope.row.icon"/>
              </div>
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="$authButton('delete')"
                style="color: red"
                type="text"
                @click="deleteRow(scope.row)"
                size="mini">删除
              </el-button>
              <el-button
                v-if="$authButton('addChild')"
                type="text"
                @click="openAdd(scope.row)"
                size="mini">新增子菜单
              </el-button>
              <el-button
                v-if="$authButton('update')"
                @click="openUpdate(scope.row)"
                type="text"
                size="mini">编辑
              </el-button>

              <el-button
                v-if="$authButton('buttonSetting')"
                @click="openButton(scope.row)"
                type="text"
                size="mini">按钮配置
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-row>

    <!--    编辑窗体-->
    <el-dialog title="编辑菜单"
               width="25%"
               :visible.sync="isShowUpdate"
               v-if="isShowUpdate"
               :close-on-click-modal="false">
      <Update :row="this.clickRow" :close-fun="this.closeUpdate"/>
    </el-dialog>
    <!--    新增窗体-->
    <el-dialog title="新增菜单"
               width="25%"
               :visible.sync="isShowAdd"
               :close-on-click-modal="false"
               v-if="isShowAdd">
      <Add :close-fun="closeAdd" :row="this.clickRow"/>
    </el-dialog>
    <!--    按钮配置-->
    <el-dialog title="按钮配置"
               :visible.sync="isShowButton"
               :close-on-click-modal="false"
               v-if="isShowButton">
      <ButtonEdit :row="this.clickRow"/>
    </el-dialog>
  </div>
</template>

<script>
  import {QueryMenuData, deleteMenu} from '@/api/menu'
  import Update from '@/views/MenuManage/Update'
  import Add from '@/views/MenuManage/Add'
  import ButtonEdit from '@/views/MenuManage/ButtonEdit'

  export default {
    name: 'MenuManage',
    components: {ButtonEdit, Update, Add},
    data() {
      return {
        clickRow: null,
        TableData: [],
        QueryData: {
          menuName: null,
          menuCode: null,
          menuUrl: null
        },
        isShowUpdate: false,
        isShowAdd: false,
        isShowButton: false
      }
    },
    mounted() {
      this.QueryTableData()
    },
    methods: {
      // 清空
      clearQueryData() {
        this.QueryData = {
          menuName: null,
          menuCode: null,
          menuUrl: null
        }
      },
      // 查询数据
      QueryTableData() {
        QueryMenuData(this.QueryData).then(res => {
          this.TableData = res
        }).catch(err => {
          this.$notify.error({
            title: '错误',
            message: err.message
          })
        })
      },
      // 打开编辑窗口
      openUpdate(row) {
        this.clickRow = row
        this.isShowUpdate = true
      },
      // 关闭编辑窗口
      closeUpdate() {
        this.isShowUpdate = false
        this.QueryTableData()
      },

      // 打开新增窗口
      openAdd(row) {
        this.clickRow = row
        this.isShowAdd = true
      },

      // 关闭新增窗口
      closeAdd() {
        this.isShowAdd = false
        this.QueryTableData()
      },

      // 打开按钮窗口
      openButton(row) {
        this.clickRow = row
        this.isShowButton = true
      },

      // 删除菜单
      deleteRow(row) {
        this.$confirm('是否删除该菜单？菜单名：' + row.menuName, '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(async () => {
          await deleteMenu(row.id).then(res => {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })
            this.QueryTableData()
          }).catch(err => {
            this.$notify.error({
              title: '失败',
              message: err.message
            })
          })
        }).catch(() => {

        })

      }
    },
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 10px;
  }

  .el-row:last-child {
    margin-bottom: 0
  }

  .el-form-item {
    margin-bottom: 0
  }

  /*强修改表格展开按钮位置*/
  >>> .el-table__expand-icon {
    position: absolute;
    left: 10px
  }
</style>
