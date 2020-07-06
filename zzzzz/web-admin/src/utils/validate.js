/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  // 用户名正则，4到16位（字母，数字，下划线）
  const uPattern = /^[a-zA-Z0-9_]{4,16}$/
  return uPattern.test(str.trim())
}

export function validPassword(str) {
  // 密码正则，6到16位（字母，数字，下划线）
  const pPattern = /^[a-zA-Z0-9_]{6,16}$/
  // 输出 true
  return pPattern.test(str.trim())
}
