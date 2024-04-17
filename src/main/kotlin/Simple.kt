import kotlinx.browser.document
import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import kotlin.math.PI
import kotlin.math.abs
//import kotlin.browser.document

fun main() {
    // Canvas 요소와 컨텍스트 가져오기
    val canvas = document.getElementById("Canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    // 기본 값 설정
    var isDrawing = false
    var lineWidth = 2
    var strokeColor = "#000000"
    var fillColor = "#ffffff"
    var downX = 0.0
    var downY = 0.0

    //텍스트 박스
    val textInput = document.getElementById("TextInput") as HTMLInputElement

    // 리스너 정의
    // 원 그리는 이벤트리스너
    val circleListener = EventListener { event ->
        ctx.lineWidth = lineWidth.toDouble()
        ctx.strokeStyle = strokeColor
        ctx.fillStyle = fillColor

        val upX = event.asDynamic().offsetX.toString().toDouble()
        val upY = event.asDynamic().offsetY.toString().toDouble()

        val centerX = (upX + downX) / 2
        val centerY = (upY + downY) / 2
        val radiusX = abs((upX - downX) / 2)
        val radiusY = abs((upY - downY) / 2)

        ctx.beginPath()
        ctx.ellipse(centerX, centerY, radiusX, radiusY, 0.0, 0.0, 2*PI)
        ctx.closePath()
        ctx.stroke()
        ctx.fill()
    }
    // 사각형 그리는 이벤트리스너
    val rectangleListener = EventListener { event ->
        ctx.lineWidth = lineWidth.toDouble()
        ctx.strokeStyle = strokeColor
        ctx.fillStyle = fillColor

        val upX = event.asDynamic().offsetX.toString().toDouble()
        val upY = event.asDynamic().offsetY.toString().toDouble()
        val subX = abs(upX - downX)
        val subY = abs(upY - downY)

        ctx.beginPath()
        ctx.rect(downX, downY, subX, subY)
        ctx.closePath()
        ctx.stroke()
        ctx.fill()
    }
    // 선 그리는 이벤트리스너
    val lineListener = EventListener { event ->
        ctx.lineWidth = lineWidth.toDouble()
        ctx.strokeStyle = strokeColor

        val upX = event.asDynamic().offsetX.toString().toDouble()
        val upY = event.asDynamic().offsetY.toString().toDouble()

        ctx.beginPath()
        ctx.moveTo(downX, downY)
        ctx.lineTo(upX, upY)
        ctx.closePath()
        ctx.stroke()
    }
    // 텍스트 이벤트리스너
    // 수 정 필 요
    val textListener = EventListener { event ->
        val upY = event.asDynamic().offsetY.toString().toDouble()
        val subY = abs(upY - downY)
        val text = textInput.value
        ctx.font = "20px Arial"
        ctx.fillStyle = fillColor
        ctx.fillText(text, downX, downY, subY)
    }
    // 버튼에 리스너 붙이기
    fun drawCircle(ctx: CanvasRenderingContext2D) {
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mouseup", circleListener)
    }
    fun drawRectangle(ctx: CanvasRenderingContext2D) {
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mouseup", rectangleListener)
    }
    fun drawLine(ctx: CanvasRenderingContext2D) {
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mouseup", lineListener)
    }
    fun drawText(ctx: CanvasRenderingContext2D, text: String) {
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.addEventListener("mouseup", textListener)
    }

    // 마우스 다운 이벤트 처리
    canvas.addEventListener("mousedown", { event ->
        isDrawing = true
        downX = event.asDynamic().offsetX.toString().toDouble()
        downY = event.asDynamic().offsetY.toString().toDouble()
    })

    // 마우스 이동 이벤트 처리
    canvas.addEventListener("mousemove", { event ->
        if (!isDrawing) return@addEventListener
        val nowX = event.asDynamic().offsetX
        val nowY = event.asDynamic().offsetY
    })

    // 마우스 업 이벤트 처리
    canvas.addEventListener("mouseup", {
        isDrawing = false
    })

    // 선 두께 변경 이벤트 처리
    val lineWidthInput = document.getElementById("LineWidth") as HTMLInputElement
    lineWidthInput.addEventListener("input", {
        lineWidth = lineWidthInput.value.toInt()
    })

    // 스트로크 색상 변경 이벤트 처리
    val strokeColorInput = document.getElementById("StrokeColor") as HTMLInputElement
    strokeColorInput.addEventListener("input", {
        strokeColor = strokeColorInput.value
    })

    // 채우기 색상 변경 이벤트 처리
    val fillColorInput = document.getElementById("FillColor") as HTMLInputElement
    fillColorInput.addEventListener("input", {
        fillColor = fillColorInput.value
    })

    // 텍스트 입력 이벤트 처리

//    textInput.addEventListener("input", {
//        drawText(ctx, textInput.value)
//    })

    // 도형 버튼 이벤트 처리
    val circleBtn = document.getElementById("Circle") as HTMLButtonElement
    val rectangleBtn = document.getElementById("Rectangle") as HTMLButtonElement
    val lineBtn = document.getElementById("Line") as HTMLButtonElement
    val textBtn = document.getElementById("Text") as HTMLButtonElement

    circleBtn.addEventListener("click", { drawCircle(ctx) })
    rectangleBtn.addEventListener("click", { drawRectangle(ctx) })
    lineBtn.addEventListener("click", { drawLine(ctx) })
    textBtn.addEventListener("click", {
        val text = textInput.value
        drawText(ctx, text)
    })
}