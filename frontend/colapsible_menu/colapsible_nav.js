'use strict';

document.querySelector('#sidebarCollapse').addEventListener(
  'click',
  handler=> document.querySelector('#sidebar').classList.toggle('active')
);

