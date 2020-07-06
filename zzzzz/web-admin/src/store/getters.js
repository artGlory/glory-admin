const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.admin.token,
  avatar: state => state.admin.avatar,
  name: state => state.admin.name,
  permissions: state => state.admin.permissions,
  system_name: state => state.admin.system.name,
  system_logo: state => state.admin.system.logo,
  system_copyright: state => state.admin.system.copyright,
  permission_routes: state => state.permission.routes
}
export default getters
