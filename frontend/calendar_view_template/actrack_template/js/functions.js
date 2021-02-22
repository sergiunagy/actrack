'use strict';

const sidebar = document.querySelector('.sidebar');
const btn_sidebar_colapse = document.querySelector('#sidebarCollapse');

btn_sidebar_colapse.addEventListener('click', ()=>  sidebar.classList.toggle('active'));
