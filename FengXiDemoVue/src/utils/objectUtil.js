// 去除对象中的空字符串为null
export function removeObjEmpty(obj) {
  let itemList = JSON.stringify(obj)
  let result = itemList.replace(/""/g, 'null')
  return JSON.parse(result)
}
