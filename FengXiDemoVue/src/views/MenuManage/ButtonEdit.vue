<template>
  <div>
    <el-card>
      <div slot="header" style="width: 100%;
        display: flex;align-items: center;
          justify-content: space-between;">
        <div style="display: flex;align-items: center;">
          <i class="el-icon-s-grid"></i>
          <span style="margin-left: 5px;">按钮数据</span>
          <el-tag v-if="row !== null" style="margin-left: 20px;">菜单名称 : {{row.menuName}}</el-tag>
        </div>
        <div>
          <el-button type="primary" @click="()=>{this.isShowAdd = true}">新增</el-button>
        </div>
      </div>

      <el-table
        :data="TableData"
        border
        :header-cell-style="{'text-align': 'center'}"
        :cell-style="{'text-align': 'center'}">
        <el-table-column
          prop="buttonName"
          label="按钮名称">
        </el-table-column>
        <el-table-column
          prop="buttonCode"
          label="按钮编号">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              @click="deleteButton(scope.row)"
              style="color: red"
              type="text"
              size="mini">删除
            </el-button>
            <el-button
              @click="()=>{
                updateData = scope.row
               isShowUpdate = true
              }"
              type="text"
              size="mini">编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!--    新增弹窗-->
    <el-dialog
      width="25%"
      v-if="isShowAdd"
      title="新增按钮"
      append-to-body
      :close-on-click-modal="false"
      :visible.sync="isShowAdd">

      <el-form label-position="right"
               label-width="120px"
               :model="addData"
               ref="addFormRef">
        <el-form-item
          :rules="[{ required: true, message: '按钮名称不能为空'}]"
          prop="buttonName"
          label="按钮名称">
          <el-input v-model="addData.buttonName" />
        </el-form-item>

        <el-form-item
          :rules="[{ required: true, message: '按钮编号不能为空'}]"
          prop="buttonCode"
          label="按钮编号">
          <el-input v-model="addData.buttonCode" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="isShowAdd = false">取消</el-button>
        <el-button type="primary" @click="addButton">提交</el-button>
      </div>
    </el-dialog>

    <!--    编辑弹窗-->
    <el-dialog
      width="25%"
      title="编辑按钮"
      append-to-body
      @close="getButtonByMenuId"
      :close-on-click-modal="false"
      :visible.sync="isShowUpdate">

      <el-form label-position="right"
               label-width="120px"
               :model="updateData"
               ref="addFormRef">
        <el-form-item
          :rules="[{ required: true, message: '按钮名称不能为空'}]"
          prop="buttonName"
          label="按钮名称">
          <el-input v-model="updateData.buttonName" />
        </el-form-item>

        <el-form-item
          :rules="[{ required: true, message: '按钮编号不能为空'}]"
          prop="buttonCode"
          label="按钮编号">
          <el-input v-model="updateData.buttonCode" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="isShowUpdate = false">取消</el-button>
        <el-button type="primary" @click="updateButton">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getButtonByMenuId, addButton, deleteButton, updateButton } from '@/api/button'

export default {
  name: 'ButtonEdit',
  data() {
    return {
      addData: {},
      updateData: {},
      TableData: [],
      isShowAdd: false,
      isShowUpdate: false
    }
  },
  props: {
    row: {
      type: Object,
      default: {}
    }
  },
  mounted() {
    this.getButtonByMenuId()
  },
  methods: {
    getButtonByMenuId() {
      getButtonByMenuId(this.row.id).then(res => {
        this.TableData = res
      }).catch(err => {
        this.$notify.error({
          title: '查询失败',
          message: err.message
        })
      })
    },
    // 新增按钮
    addButton() {
      this.$refs['addFormRef'].validate(valid => {
        if (valid) {
          let data = { ...this.addData }
          data.menuId = this.row.id
          addButton(data).then(res => {
            this.$notify.success({
              title: '新增成功',
              message: ''
            })
            this.isShowAdd = false
            this.addData = {}
            this.getButtonByMenuId()
          }).catch(err => {
            this.$notify.error({
              title: '新增失败',
              message: err.message
            })
          })
        } else {
          return false
        }
      })
    },
    // 删除按钮
    deleteButton(row) {
      this.$confirm('是否删除该按钮？按钮名：' + row.buttonName, '删除按钮', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(async() => {
        await deleteButton(row.id).then(res => {
          this.$notify.success({
            title: '成功',
            message: '删除成功'
          })
          this.getButtonByMenuId()
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
        })
      }).catch(() => {

      })
    },
    // 修改按钮数据
    updateButton() {
      let data = { ...this.updateData }
      updateButton(data).then(res => {
        this.$notify.success({
          title: '修改成功',
          message: ''
        })
        this.isShowUpdate = false
      }).catch(err => {
        this.$notify.error({
          title: '修改失败',
          message: err.message
        })
      })
    }
  }
}
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
    margin-left: 10px;
    margin-right: 10px;
  }

  .el-row:last-child {
    margin-bottom: 0
  }
</style>
