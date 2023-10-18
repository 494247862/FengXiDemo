<template>
  <div>
    <div style="text-align: center">
      <el-tag v-if="row !== null" style="margin-bottom: 20px;">父节点：{{row.menuName}}</el-tag>
    </div>
    <el-form label-position="right"
             label-width="120px"
             :model="formData"
             ref="addFormRef">
      <el-form-item
        label="菜单名称:"
        prop="menuName"
        :rules="[{ required: true, message: '菜单名称不能为空'}]">
        <el-input v-model="formData.menuName" />
      </el-form-item>

      <el-form-item label="菜单编号:"
                    prop="menuCode"
                    :rules="[{ required: true, message: '菜单编号不能为空'}]">
        <el-input v-model="formData.menuCode" />
      </el-form-item>

      <el-form-item label="菜单地址:"
                    :rules="[{ required: true, message: '菜单地址不能为空，可传入 /'}]"
                    prop="menuUrl">
        <el-input v-model="formData.menuUrl" />
      </el-form-item>

      <el-form-item label="菜单图标:"
                    prop="icon">
        <el-input v-model="formData.icon" />
      </el-form-item>

      <el-form-item label="序号:"
                    prop="number">
        <el-input-number v-model="formData.number"/>
      </el-form-item>

      <el-form-item style="text-align: end">
        <el-button type="primary" :loading="loading" @click="addMenu">提交</el-button>
        <el-button @click="closeFun">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addMenu } from '@/api/menu'

export default {
  name: 'Add',
  data() {
    return {
      loading: false,
      formData: {}
    }
  },
  props: {
    closeFun: {
      type: Function,
      default: () => {
        return false
      }
    },
    row: {
      type: Object,
      default: {}
    }
  },
  methods: {
    addMenu: function() {
      this.$refs['addFormRef'].validate(valid => {
        if (valid) {
          this.loading = true
          // 复制数据
          let data = { ...this.formData }
          if (this.row === null) { // 如果传过来的数据是空，则插入level为1的菜单
            data.level = 1
            data.parentId = null
          } else { //加入到子菜单
            data.level = this.row.level + 1
            data.parentId = this.row.id
          }
          // 开始提交
          addMenu(data).then(res => {
            this.$notify.success({
              title: '新增成功',
              message: ''
            })
            this.loading = false
            this.closeFun()
          }).catch(err => {
            this.$notify.error({
              title: '新增失败',
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
