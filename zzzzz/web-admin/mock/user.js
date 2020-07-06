const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    permissions: ['admin', 'admin1', 'admin2'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin',
    systemName: 'XX  后台操作系统',
    systemLogo: 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png',
    systemCopyright: 'Copyright 2020 © XXX.CH .Inc'
  },
  'editor-token': {
    permissions: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor',
    systemName: 'XX  后台操作系统',
    systemLogo: 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png',
    systemCopyright: 'Copyright 2020 © XXX.CH .Inc'
  }
}

const allRoles = [
  {
    uk: '2006251543117690000',
    roleName: '管理员',
    roleDesc: '描述',
    parentUk: '',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  },
  {
    uk: '2006251543117690001',
    roleName: '管理员1',
    roleDesc: '描述',
    parentUk: '2006251543117690000',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }, {
    uk: '2006251543117690002',
    roleName: '管理员2',
    roleDesc: '描述',
    parentUk: '2006251543117690001',
    createTime: '2020-06-25 15:43:31',
    updateTime: '2020-06-25 15:43:31'
  }
]
module.exports = [
  // user login
  {
    url: '/api/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          responseUUID: '',
          success: false,
          message: 'Account and password are incorrect.',
          data: {}
        }
      }

      return {
        responseUUID: '',
        success: true,
        message: '',
        data: token
      }
    }
  },

  // get user info
  {
    url: '/api/user/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]
      // mock error
      if (!info) {
        return {
          responseUUID: '',
          success: false,
          message: 'Login failed, unable to get user details.',
          data: {}
        }
      }
      return {
        responseUUID: '',
        success: true,
        message: '',
        data: info
      }
    }
  },

  // user logout
  {
    url: '/api/user/logout',
    type: 'post',
    response: _ => {
      return {
        responseUUID: '',
        success: true,
        message: '',
        data: 'success'
      }
    }
  },

  //getAllRole
  {
    url: '/api/user/getAllRoles',
    type: 'get',
    response: _ => {
      return {
        responseUUID: '',
        success: true,
        message: '',
        data: {
          allRoles: allRoles
        }
      }
    }
  }
]
