const getters = {
    sidebar: state => state.app.sidebar,
    size: state => state.app.size,
    device: state => state.app.device,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
    token: state => state.user.token,
    accountKbn: state => state.user.accountKbn,
    companyCode: state => state.user.companyCode,
    permissionKbn: state => state.user.permissionKbn,
    userId: state => state.user.userId,
    userName: state => state.user.userName,
    permission_routes: state => state.permission.routes,
    errorLogs: state => state.errorLog.logs
  }
  export default getters
  