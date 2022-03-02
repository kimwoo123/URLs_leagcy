export const listUrl = {
  baeURL: 'http://k5b201.p.ssafy.io:4000',
  release_url: 'http://k5b201.p.ssafy.io:4000/other',
};

export const CATEGORIES = [
  {type: 'CS', length: 1, value: '프론트', label: '프론트'},
  {type: 'CS', length: 1, value: '백엔드', label: '백엔드'},
  {type: 'CS', length: 1, value: '모바일', label: '모바일'},
  {type: 'CS', length: 1, value: '빅데이터/AI', label: '빅데이터/AI'},
  {type: 'CS', length: 1, value: 'CS', label: 'CS'},
  {type: 'CS', length: 1, value: 'DevOps', label: 'DevOps'},
  {type: 'CS', length: 1, value: 'Tools(생산성)', label: 'Tools(생산성)'},
  {type: 'CS', length: 1, value: '기획/디자인', label: '기획/디자인'},
];
export const CATEGORY = [
  {
    label: 'Computer Science',
    options: CATEGORIES.filter(category => category.type === 'CS'),
  },
];
