(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports'], factory);
  else if (typeof exports === 'object')
    factory(module.exports);
  else
    root.whiteboard = factory(typeof whiteboard === 'undefined' ? {} : whiteboard);
}(this, function (_) {
  'use strict';
  //region block: pre-declaration
  //endregion
  function main() {
    console.log('Hello, ' + greet());
  }
  function greet() {
    return 'world';
  }
  main();
  return _;
}));

//# sourceMappingURL=whiteboard.js.map
