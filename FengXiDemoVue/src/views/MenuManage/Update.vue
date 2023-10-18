<template>
  <div>
    <el-form label-position="right"
             label-width="120px"
             :model="formData"
             ref="updateFormRef">
      <el-form-item
        label="菜单名称:"
        prop="menuName"
        :rules="[{ required: true, message: '菜单名称不能为空'}]">
        <el-input v-model="formData.menuName"/>
      </el-form-item>

      <el-form-item label="菜单编号:"
                    prop="menuCode"
                    :rules="[{ required: true, message: '菜单编号不能为空'}]">
        <el-input v-model="formData.menuCode"/>
      </el-form-item>

      <el-form-item label="菜单地址:"
                    :rules="[{ required: true, message: '菜单地址不能为空，可传入 /'}]"
                    prop="menuUrl">
        <el-input v-model="formData.menuUrl"/>
      </el-form-item>

      <el-form-item label="菜单图标:"
                    prop="icon">
        <el-input v-model="formData.icon"/>
      </el-form-item>

      <el-form-item label="序号:"
                    prop="number">
        <el-input-number v-model="formData.number"/>
      </el-form-item>

      <el-form-item style="text-align: end">
        <el-button type="primary" :loading="loading" @click="updateData">保存</el-button>
        <el-button @click="closeFun">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {updateMenu} from '@/api/menu'

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
      this.formData = {...this.row}
    },
    methods: {
      updateData() {
        this.$refs['updateFormRef'].validate((valid) => {
          if (valid) {
            this.loading = true
            updateMenu(this.formData).then(res => {
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
