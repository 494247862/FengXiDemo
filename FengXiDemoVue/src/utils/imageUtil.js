function dataUrlToFile(dataurl,fileName) {
  let arr = dataurl.split(',');
  let mime = arr[0].match(/:(.*?);/)[1];
  // suffix是该文件的后缀
  let suffix = mime.split('/')[1];
  // atob 对经过 base-64 编码的字符串进行解码
  let bstr = atob(arr[1]);
  // n 是解码后的长度
  let n = bstr.length;
  // Uint8Array 数组类型表示一个 8 位无符号整型数组 初始值都是 数子0
  let u8arr = new Uint8Array(n);
  // charCodeAt() 方法可返回指定位置的字符的 Unicode 编码。这个返回值是 0 - 65535 之间的整数
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  // new File返回File对象 第一个参数是 ArraryBuffer 或 Bolb 或Arrary 第二个参数是文件名
  // 第三个参数是 要放到文件中的内容的 MIME 类型
  return new File([u8arr], `${fileName}`, {
    type: mime,
  });
}

/**
 * 用于修复png图片黑底的问题
 * @param file
 * @returns {Promise<unknown>}
 */
export function pngToWhiteBg(file) {
  let read = new FileReader()
  read.readAsDataURL(file) // 文件转base64
  return new Promise((resolve, reject) => {
    read.onload = (e) => {
      let img = new Image()
      img.src = e.target.result
      img.onload = async() => {
        // 生成canvas
        let canvas = document.createElement('canvas')
        let context = canvas.getContext('2d')
        // 绘制图片到canvas上
        canvas.width = img.width
        canvas.height = img.height

        // 在canvas绘制前填充白色背景
        context.fillStyle = '#fff'
        context.fillRect(0, 0, canvas.width, canvas.height)
        context.drawImage(img, 0, 0)
        let base64 = canvas.toDataURL(file['type'], 1)
        let newFile = dataUrlToFile(base64,file.name)
        resolve(newFile)
      }
    }
  })
}

