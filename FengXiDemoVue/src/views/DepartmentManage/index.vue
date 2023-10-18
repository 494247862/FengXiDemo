<template>
  <div class="app-container">
    <el-row style="height: calc(100%)">
      <el-card style="height: 100%" body-style="height: 100%">
        <!--        头部-->
        <div slot="header" style="width: 100%;
        display: flex;align-items: center;
          justify-content: space-between;">
          <div>
            <i class="el-icon-s-grid"></i>
            <span style="margin-left: 5px;">部门数据</span>
          </div>
          <div>
            <el-button size="small" type="primary" @click="openAdd(null)">新增</el-button>
          </div>
        </div>
        <!--        数据显示-->
        <el-table
          row-key="id"
          lazy
          :tree-props="{children:'children',hasChildren:true}"
          :data="TableData"
          :header-cell-style="{'text-align': 'center'}"
          :cell-style="{'text-align': 'center'}"
          border
          style="width: 100%;height: calc(100% - 60px);overflow-y: auto">
          <el-table-column
            prop="departmentCode"
            align="center"
            label="部门编号">
          </el-table-column>

          <el-table-column
            prop="departmentName"
            align="center"
            label="部门名称">
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button
                @click="openAdd(scope.row)"
                type="text"
                size="mini">新增子部门
              </el-button>
              <el-button
                @click="openUpdate(scope.row)"
                type="text"
                size="mini">编辑
              </el-button>
              <el-button
                style="color: red"
                type="text"
                @click="deleteDepartment(scope.row)"
                size="mini">删除
              </el-button>
            </template>
          </el-table-column>

        </el-table>
      </el-card>
    </el-row>
    <el-dialog title="新增部门"
               width="25%"
               v-if="isShowAdd"
               :close-on-click-modal="false"
               :visible.sync="isShowAdd">
      <Add :row="this.clickRow" :close-fun="closeAdd"/>
    </el-dialog>

    <el-dialog title="编辑部门"
               v-if="isShowUpdate"
               :close-on-click-modal="false"
               :visible.sync="isShowUpdate"
               width="25%">
      <Update :row="this.clickRow" :close-fun="this.closeUpdate"/>
    </el-dialog>
  </div>
</template>

<script>
  import Add from '@/views/DepartmentManage/Add'
  import {getDepartmentTree, deleteDepartment} from '@/api/department'
  import Update from '@/views/DepartmentManage/Update'

  export default {
    name: 'DepartmentManage',
    components: {Update, Add},
    data() {
      return {
        isShowAdd: false,
        isShowUpdate: false,
        clickRow: null,
        TableData: []
      }
    },
    mounted() {
      this.QueryTableData()
    },
    methods: {
      QueryTableData() {
        getDepartmentTree().then(res => {
          this.TableData = res
        }).catch(err => {
          this.$notify.error({
            title: '错误',
            message: err.message
          })
        })
      },
      deleteDepartment(row) {
        this.$confirm('是否删除该部门？部门名：' + row.departmentName, '删除部门', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(async () => {
          deleteDepartment(row.id).then(res => {
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
        }).catch(err => {

        })

      },
      openAdd(row) {
        this.isShowAdd = true
        this.clickRow = row
      },
      openUpdate(row) {
        this.clickRow = row
        this.isShowUpdate = true
      },
      closeAdd() {
        this.isShowAdd = false
        this.QueryTableData()
      },
      // 关闭编辑窗口
      closeUpdate() {
        this.isShowUpdate = false
        this.QueryTableData()
      }
    }
  }
</script>

<style scoped>
  /*强修改表格展开按钮位置*/
  >>> .el-table__expand-icon {
    position: absolute;
    left: 10px
  }
</style>
