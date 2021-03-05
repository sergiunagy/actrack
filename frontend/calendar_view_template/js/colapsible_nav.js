'use strict';

document.querySelector('#sidebarCollapse').addEventListener(
  'click',
  handler=> document.querySelector('#sidebar').classList.toggle('active')
);

document.querySelectorAll('.calendar-cell').forEach(cell=> cell.classList.add('mx-1'))
