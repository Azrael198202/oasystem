import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/view/Login'
import Registered from '@/view/registered'
import Toppage from '@/view/Toppage'
import NewsList from '@/view/NewsList'
import NewsDetailed from '@/view/NewsDetailed'
import Attendance from '@/view/Attendance'
import Recognize from '@/view/Recognize'
import HolidayList from '@/view/HolidayList'
import DayCheckHistory from '@/view/DayCheckHistory'
import Maintenance from '@/view/Maintenance'
import PaidHolidayMaintenance from '@/view/PaidHolidayMaintenance'
import SubHolidayInput from '@/view/SubHolidayInput'
import PrivilegeManagement from '@/view/PrivilegeManagement'
import Demo from '@/view/Demo'
import NoticeMaintenance from '@/view/NoticeMaintenance'
import AttendanceExport from '@/view/AttendanceExport'
import Resume from '@/view/Resume'
import ResumeDetail from '@/view/ResumeDetail'
import ResumeMaster from '@/view/ResumeMaster'

import Csstest from '@/view/Csstest'
// 2021/08/04 ADD START
import ClockInAndOut from '@/view/ClockInAndOut'
// 2021/08/04 ADD END
import ApplyAll from '@/view/ApplyAll'
import Pwreset from '@/view/Pwreset'
import AttendanceApply from '@/view/AttendanceApply'

import PersonalInfo from '@/view/PersonalInfo.vue'

import { getToken } from '@/utils/auth'
import store from '@/store'
import { MessageBox, Message } from 'element-ui'


Vue.use(Router)

const router = new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/registered',
      name: 'Registered',
      component: Registered
    },
    {
      path: '/toppage',
      name: 'Toppage',
      component: Toppage
    },
    {
      path: '/attendance',
      name: 'Attendance',
      component: Attendance
    },
// 2021/08/04 ADD START
    {
      path: '/clockInAndOut',
      name: 'ClockInAndOut',
      component: ClockInAndOut
    },
// 2021/08/04 ADD END
    {
      path: '/newsList',
      name: 'NewsList',
      component: NewsList
    },
    {
      path: '/newsDetailed',
      name: 'NewsDetailed',
      component: NewsDetailed
    },
    {
      path: '/applyAll',
      name: 'ApplyAll',
      component: ApplyAll
    },
    {
      path: '/attendanceApply',
      name: 'AttendanceApply',
      component: AttendanceApply
    },
    {
      path: '/recognize',
      name: 'Recognize',
      component: Recognize
    },
    {
      path: '/holidayList',
      name: 'HolidayList',
      component: HolidayList
    },
    {
      path: '/dayCheckHistory',
      name: 'DayCheckHistory',
      component: DayCheckHistory
    },
    {
      path: '/maintenance',
      name: 'Maintenance',
      component: Maintenance
    },
    {
      path: '/paidHolidayMaintenance',
      name: 'paidHolidayMaintenance',
      component: PaidHolidayMaintenance
    },
    {
      path: '/subHolidayInput',
      name: 'subHolidayInput',
      component: SubHolidayInput
    },
    {
      path: '/privilegeManagement',
      name: 'privilegeManagement',
      component: PrivilegeManagement
    },
    {
      path: '/attendanceExport',
      name: 'AttendanceExport',
      component: AttendanceExport
    },
    {
      path: '/demo',
      name: 'Demo',
      component: Demo
    },
    {
      path: '/resume',
      name: 'Resume',
      component: Resume
    },
    {
      path: '/resumeDetail',
      name: 'ResumeDetail',
      component: ResumeDetail
    },
    {
      path: '/resumeMaster',
      name: 'ResumeMaster',
      component: ResumeMaster
    },
    {
      path: '/noticeMaintenance',
      name: 'NoticeMaintenance',
      component: NoticeMaintenance
    },

    {
      path: '/csstest',
      name: 'Csstest',
      component: Csstest
    },
    {
      // 本番環境でリリースする時、コメントにする
      path: '/pwreset',
      name: 'Pwreset',
      component: Pwreset
    },
    {
      path: '/personalInfo',
      name: 'PersonalInfo',
      component: PersonalInfo
    }
  ]
});

//获取原型对象上的push函数
const originalPush = Router.prototype.push
//修改原型对象中的push方法
Router.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {

  if (from.path === '/toppage' && to.path === '/attendance') {
    sessionStorage.setItem("changeFlg",'1');
    // トップページから申請一覧
  }else if (from.path === '/toppage' && to.path === '/applyAll') {
    sessionStorage.setItem("changeFlg",'1');
    // 勤怠から申請一覧
  }else if (from.path === '/attendance' && to.path === '/applyAll') {
    sessionStorage.setItem("changeFlg",'1');
  }

  if (to.path === '/login' || to.path === '/registered') {
    next();
  } else {
      let token = getToken('Admin-Token') || getQueryVariable('token');
      console.log('token = '+token)
      if (token === null || token === '' || token === undefined) {
        next('/login');
        Message({
          message: 'ログインの有効期限が切れました。もう一度ログインしてください',
          type: 'error',
          duration: 4 * 1000
        })
      } else {
        next();
      }
  }
});

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

// 用于获取url参数
export function getQueryVariable(variable) {
  var query = window.location.search.substring(1)
  var vars = query.split('&')
  for (var i = 0; i < vars.length; i++) {
      var pair = vars[i].split('=')
      if (pair[0] === variable) {
      return pair[1]
      }
  }
  return false
}

export default router;
