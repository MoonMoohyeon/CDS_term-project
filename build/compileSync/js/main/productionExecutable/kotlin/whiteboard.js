(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib-js-ir.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib-js-ir.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib-js-ir'] === 'undefined') {
      throw new Error("Error loading module 'whiteboard'. Its dependency 'kotlin-kotlin-stdlib-js-ir' was not found. Please, check whether 'kotlin-kotlin-stdlib-js-ir' is loaded prior to 'whiteboard'.");
    }
    root.whiteboard = factory(typeof whiteboard === 'undefined' ? {} : whiteboard, this['kotlin-kotlin-stdlib-js-ir']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var THROW_CCE = kotlin_kotlin.$_$.d;
  var Unit_getInstance = kotlin_kotlin.$_$.a;
  var toInt = kotlin_kotlin.$_$.c;
  var get_PI = kotlin_kotlin.$_$.b;
  //endregion
  //region block: pre-declaration
  //endregion
  function main() {
    var tmp = document.getElementById('Canvas');
    var canvas = tmp instanceof HTMLCanvasElement ? tmp : THROW_CCE();
    var tmp_0 = canvas.getContext('2d');
    var ctx = tmp_0 instanceof CanvasRenderingContext2D ? tmp_0 : THROW_CCE();
    var isDrawing = {_v: false};
    var lineWidth = {_v: 2};
    var strokeColor = {_v: '#000000'};
    var fillColor = {_v: '#ffffff'};
    var startX = {_v: 0.0};
    var startY = {_v: 0.0};
    canvas.addEventListener('mousedown', main$lambda(isDrawing, startX, startY));
    canvas.addEventListener('mousemove', main$lambda_0(isDrawing));
    canvas.addEventListener('mouseup', main$lambda_1(isDrawing));
    var tmp_1 = document.getElementById('LineWidth');
    var lineWidthInput = tmp_1 instanceof HTMLInputElement ? tmp_1 : THROW_CCE();
    lineWidthInput.addEventListener('input', main$lambda_2(lineWidth, lineWidthInput));
    var tmp_2 = document.getElementById('StrokeColor');
    var strokeColorInput = tmp_2 instanceof HTMLInputElement ? tmp_2 : THROW_CCE();
    strokeColorInput.addEventListener('input', main$lambda_3(strokeColor, strokeColorInput));
    var tmp_3 = document.getElementById('FillColor');
    var fillColorInput = tmp_3 instanceof HTMLInputElement ? tmp_3 : THROW_CCE();
    fillColorInput.addEventListener('input', main$lambda_4(fillColor, fillColorInput));
    var tmp_4 = document.getElementById('TextInput');
    var textInput = tmp_4 instanceof HTMLInputElement ? tmp_4 : THROW_CCE();
    textInput.addEventListener('input', main$lambda_5(ctx, textInput, canvas, fillColor));
    var tmp_5 = document.getElementById('Circle');
    var circleBtn = tmp_5 instanceof HTMLButtonElement ? tmp_5 : THROW_CCE();
    var tmp_6 = document.getElementById('Rectangle');
    var rectangleBtn = tmp_6 instanceof HTMLButtonElement ? tmp_6 : THROW_CCE();
    var tmp_7 = document.getElementById('Line');
    var lineBtn = tmp_7 instanceof HTMLButtonElement ? tmp_7 : THROW_CCE();
    var tmp_8 = document.getElementById('Text');
    var textBtn = tmp_8 instanceof HTMLButtonElement ? tmp_8 : THROW_CCE();
    circleBtn.addEventListener('click', main$lambda_6(ctx, startX, startY, canvas, lineWidth, strokeColor, fillColor));
    rectangleBtn.addEventListener('click', main$lambda_7(ctx, canvas, lineWidth, strokeColor, fillColor, startX, startY));
    lineBtn.addEventListener('click', main$lambda_8(ctx, canvas, lineWidth, strokeColor, startX, startY));
    textBtn.addEventListener('click', main$lambda_9(textInput, ctx, canvas, fillColor));
  }
  function main$drawCircle(canvas, lineWidth, strokeColor, fillColor, startX, startY, ctx, x, y) {
    canvas.addEventListener('mouseup', main$drawCircle$lambda(ctx, lineWidth, strokeColor, fillColor, startX, startY, x));
  }
  function main$drawRectangle(canvas, lineWidth, strokeColor, fillColor, startX, startY, ctx) {
    canvas.addEventListener('mouseup', main$drawRectangle$lambda(ctx, canvas, lineWidth, strokeColor, fillColor, startX, startY));
  }
  function main$drawLine(canvas, lineWidth, strokeColor, startX, startY, ctx) {
    canvas.addEventListener('mouseup', main$drawLine$lambda(ctx, canvas, lineWidth, strokeColor, startX, startY));
  }
  function main$drawText(canvas, fillColor, ctx, text) {
    ctx.clearRect(0.0, 0.0, canvas.width, canvas.height);
    ctx.font = '20px Arial';
    ctx.fillStyle = fillColor._v;
    ctx.fillText(text, 50.0, 50.0);
  }
  function main$lambda($isDrawing, $startX, $startY) {
    return function (event) {
      $isDrawing._v = true;
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      $startX._v = tmp$ret$0.offsetX;
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      $startY._v = tmp$ret$1.offsetY;
      return Unit_getInstance();
    };
  }
  function main$lambda_0($isDrawing) {
    return function (event) {
      var tmp;
      if (!$isDrawing._v) {
        return Unit_getInstance();
      }
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var x = tmp$ret$0.offsetX;
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var y = tmp$ret$1.offsetY;
      return Unit_getInstance();
    };
  }
  function main$lambda_1($isDrawing) {
    return function (it) {
      $isDrawing._v = false;
      return Unit_getInstance();
    };
  }
  function main$lambda_2($lineWidth, $lineWidthInput) {
    return function (it) {
      $lineWidth._v = toInt($lineWidthInput.value);
      return Unit_getInstance();
    };
  }
  function main$lambda_3($strokeColor, $strokeColorInput) {
    return function (it) {
      $strokeColor._v = $strokeColorInput.value;
      return Unit_getInstance();
    };
  }
  function main$lambda_4($fillColor, $fillColorInput) {
    return function (it) {
      $fillColor._v = $fillColorInput.value;
      return Unit_getInstance();
    };
  }
  function main$lambda_5($ctx, $textInput, $canvas, $fillColor) {
    return function (it) {
      main$drawText($canvas, $fillColor, $ctx, $textInput.value);
      return Unit_getInstance();
    };
  }
  function main$lambda_6($ctx, $startX, $startY, $canvas, $lineWidth, $strokeColor, $fillColor) {
    return function (it) {
      main$drawCircle($canvas, $lineWidth, $strokeColor, $fillColor, $startX, $startY, $ctx, $startX._v, $startY._v);
      return Unit_getInstance();
    };
  }
  function main$lambda_7($ctx, $canvas, $lineWidth, $strokeColor, $fillColor, $startX, $startY) {
    return function (it) {
      main$drawRectangle($canvas, $lineWidth, $strokeColor, $fillColor, $startX, $startY, $ctx);
      return Unit_getInstance();
    };
  }
  function main$lambda_8($ctx, $canvas, $lineWidth, $strokeColor, $startX, $startY) {
    return function (it) {
      main$drawLine($canvas, $lineWidth, $strokeColor, $startX, $startY, $ctx);
      return Unit_getInstance();
    };
  }
  function main$lambda_9($textInput, $ctx, $canvas, $fillColor) {
    return function (it) {
      var text = $textInput.value;
      main$drawText($canvas, $fillColor, $ctx, text);
      return Unit_getInstance();
    };
  }
  function main$drawCircle$lambda($ctx, $lineWidth, $strokeColor, $fillColor, $startX, $startY, $x) {
    return function (event) {
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      $ctx.fillStyle = $fillColor._v;
      $ctx.beginPath();
      var tmp = $startX._v;
      var tmp_0 = $startY._v;
      var tmp$ret$0;
      // Inline function 'kotlin.math.abs' call
      var tmp0_abs = $x - $startX._v;
      tmp$ret$0 = Math.abs(tmp0_abs);
      $ctx.arc(tmp, tmp_0, tmp$ret$0, 0.0, 2 * get_PI());
      $ctx.closePath();
      $ctx.stroke();
      $ctx.fill();
      return Unit_getInstance();
    };
  }
  function main$drawRectangle$lambda($ctx, $canvas, $lineWidth, $strokeColor, $fillColor, $startX, $startY) {
    return function (event) {
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var x = tmp$ret$0.offsetX;
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var y = tmp$ret$1.offsetY;
      $ctx.clearRect(0.0, 0.0, $canvas.width, $canvas.height);
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      $ctx.fillStyle = $fillColor._v;
      $ctx.beginPath();
      $ctx.rect($startX._v, $startY._v, x - $startX._v, y - $startY._v);
      $ctx.closePath();
      $ctx.stroke();
      $ctx.fill();
      return Unit_getInstance();
    };
  }
  function main$drawLine$lambda($ctx, $canvas, $lineWidth, $strokeColor, $startX, $startY) {
    return function (event) {
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var x = tmp$ret$0.offsetX;
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var y = tmp$ret$1.offsetY;
      $ctx.clearRect(0.0, 0.0, $canvas.width, $canvas.height);
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      $ctx.beginPath();
      $ctx.moveTo($startX._v, $startY._v);
      $ctx.lineTo(x, y);
      $ctx.closePath();
      $ctx.stroke();
      return Unit_getInstance();
    };
  }
  main();
  return _;
}));

//# sourceMappingURL=whiteboard.js.map
