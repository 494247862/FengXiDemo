export function genSizeStyledIframeHtml(iframeHtml, width, height) {
  const $iframe = $(iframeHtml)
  $iframe.attr('width', width)
  $iframe.attr('height', height)
  return $iframe[0].outerHTML
}
