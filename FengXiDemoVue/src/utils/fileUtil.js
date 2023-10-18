//基础地址
const baseURL = process.env.VUE_APP_BASE_API

/**
 * 下载文件
 * res: 二进制流
 * name: 文件名
 */
export function downloadFile(res, name) {
  const blob = new Blob([res], {
    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
  })

  const link = document.createElement('a')
  // 下载后的文件名
  link.download = name

  link.href = URL.createObjectURL(blob)
  document.body.appendChild(link)
  link.click()
  //释放URL对象
  URL.revokeObjectURL(link.href)
  document.body.removeChild(link)
}

export function downloadFileForAndroid(id, name) {
  let url = "download:"+baseURL + `/file/getImage/${id}`
  const link = document.createElement('a')
  link.href = url

  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

