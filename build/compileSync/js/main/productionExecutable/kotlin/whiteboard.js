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
  var THROW_CCE = kotlin_kotlin.$_$.f;
  var EventListener = kotlin_kotlin.$_$.g;
  var Unit_getInstance = kotlin_kotlin.$_$.a;
  var toString = kotlin_kotlin.$_$.b;
  var toDouble = kotlin_kotlin.$_$.d;
  var get_PI = kotlin_kotlin.$_$.c;
  var toInt = kotlin_kotlin.$_$.e;
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
    var downX = {_v: 0.0};
    var downY = {_v: 0.0};
    var tmp_1 = document.getElementById('TextInput');
    var textInput = tmp_1 instanceof HTMLInputElement ? tmp_1 : THROW_CCE();
    var circleListener = EventListener(main$lambda(ctx, lineWidth, strokeColor, fillColor, downX, downY));
    var rectangleListener = EventListener(main$lambda_0(ctx, lineWidth, strokeColor, fillColor, downX, downY));
    var lineListener = EventListener(main$lambda_1(ctx, lineWidth, strokeColor, downX, downY));
    var textListener = EventListener(main$lambda_2(downY, textInput, ctx, fillColor, downX));
    canvas.addEventListener('mousedown', main$lambda_3(isDrawing, downX, downY));
    canvas.addEventListener('mousemove', main$lambda_4(isDrawing));
    canvas.addEventListener('mouseup', main$lambda_5(isDrawing));
    var tmp_2 = document.getElementById('LineWidth');
    var lineWidthInput = tmp_2 instanceof HTMLInputElement ? tmp_2 : THROW_CCE();
    lineWidthInput.addEventListener('input', main$lambda_6(lineWidth, lineWidthInput));
    var tmp_3 = document.getElementById('StrokeColor');
    var strokeColorInput = tmp_3 instanceof HTMLInputElement ? tmp_3 : THROW_CCE();
    strokeColorInput.addEventListener('input', main$lambda_7(strokeColor, strokeColorInput));
    var tmp_4 = document.getElementById('FillColor');
    var fillColorInput = tmp_4 instanceof HTMLInputElement ? tmp_4 : THROW_CCE();
    fillColorInput.addEventListener('input', main$lambda_8(fillColor, fillColorInput));
    var tmp_5 = document.getElementById('Circle');
    var circleBtn = tmp_5 instanceof HTMLButtonElement ? tmp_5 : THROW_CCE();
    var tmp_6 = document.getElementById('Rectangle');
    var rectangleBtn = tmp_6 instanceof HTMLButtonElement ? tmp_6 : THROW_CCE();
    var tmp_7 = document.getElementById('Line');
    var lineBtn = tmp_7 instanceof HTMLButtonElement ? tmp_7 : THROW_CCE();
    var tmp_8 = document.getElementById('Text');
    var textBtn = tmp_8 instanceof HTMLButtonElement ? tmp_8 : THROW_CCE();
    circleBtn.addEventListener('click', main$lambda_9(ctx, canvas, rectangleListener, lineListener, textListener, circleListener));
    rectangleBtn.addEventListener('click', main$lambda_10(ctx, canvas, circleListener, lineListener, textListener, rectangleListener));
    lineBtn.addEventListener('click', main$lambda_11(ctx, canvas, circleListener, rectangleListener, textListener, lineListener));
    textBtn.addEventListener('click', main$lambda_12(textInput, ctx, canvas, circleListener, rectangleListener, lineListener, textListener));
  }
  function main$drawCircle(canvas, rectangleListener, lineListener, textListener, circleListener, ctx) {
    canvas.removeEventListener('mouseup', rectangleListener);
    canvas.removeEventListener('mouseup', lineListener);
    canvas.removeEventListener('mouseup', textListener);
    canvas.addEventListener('mouseup', circleListener);
  }
  function main$drawRectangle(canvas, circleListener, lineListener, textListener, rectangleListener, ctx) {
    canvas.removeEventListener('mouseup', circleListener);
    canvas.removeEventListener('mouseup', lineListener);
    canvas.removeEventListener('mouseup', textListener);
    canvas.addEventListener('mouseup', rectangleListener);
  }
  function main$drawLine(canvas, circleListener, rectangleListener, textListener, lineListener, ctx) {
    canvas.removeEventListener('mouseup', circleListener);
    canvas.removeEventListener('mouseup', rectangleListener);
    canvas.removeEventListener('mouseup', textListener);
    canvas.addEventListener('mouseup', lineListener);
  }
  function main$drawText(canvas, circleListener, rectangleListener, lineListener, textListener, ctx, text) {
    canvas.removeEventListener('mouseup', circleListener);
    canvas.removeEventListener('mouseup', rectangleListener);
    canvas.removeEventListener('mouseup', lineListener);
    canvas.addEventListener('mouseup', textListener);
  }
  function main$lambda($ctx, $lineWidth, $strokeColor, $fillColor, $downX, $downY) {
    return function (event) {
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      $ctx.fillStyle = $fillColor._v;
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var upX = toDouble(toString(tmp$ret$0.offsetX));
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var upY = toDouble(toString(tmp$ret$1.offsetY));
      var centerX = (upX + $downX._v) / 2;
      var centerY = (upY + $downY._v) / 2;
      var tmp$ret$2;
      // Inline function 'kotlin.math.abs' call
      var tmp0_abs = (upX - $downX._v) / 2;
      tmp$ret$2 = Math.abs(tmp0_abs);
      var radiusX = tmp$ret$2;
      var tmp$ret$3;
      // Inline function 'kotlin.math.abs' call
      var tmp1_abs = (upY - $downY._v) / 2;
      tmp$ret$3 = Math.abs(tmp1_abs);
      var radiusY = tmp$ret$3;
      $ctx.beginPath();
      $ctx.ellipse(centerX, centerY, radiusX, radiusY, 0.0, 0.0, 2 * get_PI());
      $ctx.closePath();
      $ctx.stroke();
      $ctx.fill();
      return Unit_getInstance();
    };
  }
  function main$lambda_0($ctx, $lineWidth, $strokeColor, $fillColor, $downX, $downY) {
    return function (event) {
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      $ctx.fillStyle = $fillColor._v;
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var upX = toDouble(toString(tmp$ret$0.offsetX));
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var upY = toDouble(toString(tmp$ret$1.offsetY));
      var tmp$ret$2;
      // Inline function 'kotlin.math.abs' call
      var tmp0_abs = upX - $downX._v;
      tmp$ret$2 = Math.abs(tmp0_abs);
      var subX = tmp$ret$2;
      var tmp$ret$3;
      // Inline function 'kotlin.math.abs' call
      var tmp1_abs = upY - $downY._v;
      tmp$ret$3 = Math.abs(tmp1_abs);
      var subY = tmp$ret$3;
      $ctx.beginPath();
      $ctx.rect($downX._v, $downY._v, subX, subY);
      $ctx.closePath();
      $ctx.stroke();
      $ctx.fill();
      return Unit_getInstance();
    };
  }
  function main$lambda_1($ctx, $lineWidth, $strokeColor, $downX, $downY) {
    return function (event) {
      $ctx.lineWidth = $lineWidth._v;
      $ctx.strokeStyle = $strokeColor._v;
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var upX = toDouble(toString(tmp$ret$0.offsetX));
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var upY = toDouble(toString(tmp$ret$1.offsetY));
      $ctx.beginPath();
      $ctx.moveTo($downX._v, $downY._v);
      $ctx.lineTo(upX, upY);
      $ctx.closePath();
      $ctx.stroke();
      return Unit_getInstance();
    };
  }
  function main$lambda_2($downY, $textInput, $ctx, $fillColor, $downX) {
    return function (event) {
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var upY = toDouble(toString(tmp$ret$0.offsetY));
      var tmp$ret$1;
      // Inline function 'kotlin.math.abs' call
      var tmp0_abs = upY - $downY._v;
      tmp$ret$1 = Math.abs(tmp0_abs);
      var subY = tmp$ret$1;
      var text = $textInput.value;
      $ctx.font = '20px Arial';
      $ctx.fillStyle = $fillColor._v;
      $ctx.fillText(text, $downX._v, $downY._v, subY);
      return Unit_getInstance();
    };
  }
  function main$lambda_3($isDrawing, $downX, $downY) {
    return function (event) {
      $isDrawing._v = true;
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      $downX._v = toDouble(toString(tmp$ret$0.offsetX));
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      $downY._v = toDouble(toString(tmp$ret$1.offsetY));
      return Unit_getInstance();
    };
  }
  function main$lambda_4($isDrawing) {
    return function (event) {
      var tmp;
      if (!$isDrawing._v) {
        return Unit_getInstance();
      }
      var tmp$ret$0;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$0 = event;
      var nowX = tmp$ret$0.offsetX;
      var tmp$ret$1;
      // Inline function 'kotlin.js.asDynamic' call
      tmp$ret$1 = event;
      var nowY = tmp$ret$1.offsetY;
      return Unit_getInstance();
    };
  }
  function main$lambda_5($isDrawing) {
    return function (it) {
      $isDrawing._v = false;
      return Unit_getInstance();
    };
  }
  function main$lambda_6($lineWidth, $lineWidthInput) {
    return function (it) {
      $lineWidth._v = toInt($lineWidthInput.value);
      return Unit_getInstance();
    };
  }
  function main$lambda_7($strokeColor, $strokeColorInput) {
    return function (it) {
      $strokeColor._v = $strokeColorInput.value;
      return Unit_getInstance();
    };
  }
  function main$lambda_8($fillColor, $fillColorInput) {
    return function (it) {
      $fillColor._v = $fillColorInput.value;
      return Unit_getInstance();
    };
  }
  function main$lambda_9($ctx, $canvas, $rectangleListener, $lineListener, $textListener, $circleListener) {
    return function (it) {
      main$drawCircle($canvas, $rectangleListener, $lineListener, $textListener, $circleListener, $ctx);
      return Unit_getInstance();
    };
  }
  function main$lambda_10($ctx, $canvas, $circleListener, $lineListener, $textListener, $rectangleListener) {
    return function (it) {
      main$drawRectangle($canvas, $circleListener, $lineListener, $textListener, $rectangleListener, $ctx);
      return Unit_getInstance();
    };
  }
  function main$lambda_11($ctx, $canvas, $circleListener, $rectangleListener, $textListener, $lineListener) {
    return function (it) {
      main$drawLine($canvas, $circleListener, $rectangleListener, $textListener, $lineListener, $ctx);
      return Unit_getInstance();
    };
  }
  function main$lambda_12($textInput, $ctx, $canvas, $circleListener, $rectangleListener, $lineListener, $textListener) {
    return function (it) {
      var text = $textInput.value;
      main$drawText($canvas, $circleListener, $rectangleListener, $lineListener, $textListener, $ctx, text);
      return Unit_getInstance();
    };
  }
  main();
  return _;
}));

//# sourceMappingURL=whiteboard.js.map
