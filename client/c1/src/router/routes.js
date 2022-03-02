import About from '../views/components/About';
import Login from '../views/components/Login';
import RealFolder from '../views/components/RealFolder';
import Releases from '../views/components/Releases';
import Option from '../views/components/Url';

export default [
  {
    icon: 'el-icon-setting',
    name: '로그인',
    path: '/',
    component: Login,
  },
  {
    icon: 'el-icon-s-promotion',
    name: 'URL 입력',
    path: '/inject',
    component: Option,
  },
  {
    icon: 'el-icon-s-platform',
    name: '나의 폴더',
    path: '/folders',
    component: RealFolder,
  },
  {
    icon: 'el-icon-bell',
    name: '공지사항',
    path: '/releases',
    needBadge: true,
    component: Releases,
  },
  {
    icon: 'el-icon-message',
    name: '정보',
    path: '/about',
    component: About,
  },
];
