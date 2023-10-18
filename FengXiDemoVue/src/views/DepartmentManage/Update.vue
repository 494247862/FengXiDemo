<template>
  <div>
    <el-form label-position="right"
             label-width="120px"
             :model="formData"
             ref="updateFormRef">
      <el-form-item
        label="部门编号:"
        prop="departmentCode"
        :rules="[{ required: true, message: '部门编号不能为空'}]">
        <el-input v-model="formData.departmentCode"/>
      </el-form-item>

      <el-form-item
        label="部门名称:"
        prop="departmentName"
        :rules="[{ required: true, message: '部门名称不能为空'}]">
        <el-input v-model="formData.departmentName"/>
      </el-form-item>

      <el-form-item style="text-align: end;margin-bottom: 0">
        <el-button type="primary" :loading="loading" @click="updateDepartment">提交</el-button>
        <el-button @click="closeFun">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { updateDepartment } from '@/api/department'

  export default {
    name: 'Update',
    data() {
      return {
        loading: false,
        formData: {}
      }
    },
    props: {
      row: {
        type: Object,
        default: {}
      },
      closeFun: {
        type: Function
      }
    },
    mounted() {
      this.formData = { ...this.row }
    },
    methods: {
      updateDepartment() {
        this.$refs['updateFormRef'].validate(valid => {
          if (valid) {
            this.loading = true
            updateDepartment(this.formData).then(res => {
              this.$notify.success({
                title: '成功',
                message: '更新成功'
              })
              this.loading = false
              this.closeFun()
            }).catch(err => {
              this.$notify.error({
                title: '失败',
                message: err.message
              })
              this.loading = false
            })
          } else {
            return false
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
