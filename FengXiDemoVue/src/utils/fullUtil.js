export function divw(res) {
  const clientWidth = document.documentElement.clientWidth
  if (!clientWidth) return
  const fs = (clientWidth / 1920)
  return res * fs
}

export function divh(res) {
  const clientHeight = document.documentElement.clientHeight
  if (!clientHeight) return
  const fs = (clientHeight / 1080)
  return res * fs
}
