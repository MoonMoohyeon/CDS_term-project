import kotlinx.browser.document
import org.w3c.dom.*
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
    var startX = 0.0
    var startY = 0.0

    // 함수 정의
    fun drawShape(ctx: CanvasRenderingContext2D, x: Double, y: Double) {
////        ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
//        ctx.lineWidth = lineWidth.toDouble()
//        ctx.strokeStyle = strokeColor
//        ctx.fillStyle = fillColor
//
//        // 각 버튼에 따라 그리기 로직 변경 가능
//        ctx.beginPath()
//        ctx.arc(startX, startY, abs(x - startX), 0.0, 2 * PI)
//        ctx.closePath()
//        ctx.stroke()
//        ctx.fill()
    }

    fun drawCircle(ctx: CanvasRenderingContext2D, x: Double, y: Double) {
        canvas.addEventListener("mouseup", { event ->
            ctx.lineWidth = lineWidth.toDouble()
            ctx.strokeStyle = strokeColor
            ctx.fillStyle = fillColor

            // 각 버튼에 따라 그리기 로직 변경 가능
            ctx.beginPath()
            ctx.arc(startX, startY, abs(x - startX), 0.0, 2 * PI)
            ctx.closePath()
            ctx.stroke()
            ctx.fill()
        })
    }

    fun drawRectangle(ctx: CanvasRenderingContext2D) {
        canvas.addEventListener("mouseup", { event ->
            val x = event.asDynamic().offsetX
            val y = event.asDynamic().offsetY
            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            ctx.lineWidth = lineWidth.toDouble()
            ctx.strokeStyle = strokeColor
            ctx.fillStyle = fillColor
            ctx.beginPath()
            ctx.rect(startX, startY, x - startX, y - startY)
            ctx.closePath()
            ctx.stroke()
            ctx.fill()
        })
    }

    fun drawLine(ctx: CanvasRenderingContext2D) {
        canvas.addEventListener("mouseup", { event ->
            val x = event.asDynamic().offsetX
            val y = event.asDynamic().offsetY
            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            ctx.lineWidth = lineWidth.toDouble()
            ctx.strokeStyle = strokeColor
            ctx.beginPath()
            ctx.moveTo(startX, startY)
            ctx.lineTo(x, y)
            ctx.closePath()
            ctx.stroke()
        })
    }

    fun drawText(ctx: CanvasRenderingContext2D, text: String) {
        ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        ctx.font = "20px Arial"
        ctx.fillStyle = fillColor
        ctx.fillText(text, 50.0, 50.0)
    }

    // 마우스 다운 이벤트 처리
    canvas.addEventListener("mousedown", { event ->
        isDrawing = true
        startX = event.asDynamic().offsetX
        startY = event.asDynamic().offsetY
    })

    // 마우스 이동 이벤트 처리
    canvas.addEventListener("mousemove", { event ->
        if (!isDrawing) return@addEventListener
        val x = event.asDynamic().offsetX
        val y = event.asDynamic().offsetY
//        drawShape(ctx, x, y)
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
    val textInput = document.getElementById("TextInput") as HTMLInputElement
    textInput.addEventListener("input", {
        drawText(ctx, textInput.value)
    })

    // 도형 버튼 이벤트 처리
    val circleBtn = document.getElementById("Circle") as HTMLButtonElement
    val rectangleBtn = document.getElementById("Rectangle") as HTMLButtonElement
    val lineBtn = document.getElementById("Line") as HTMLButtonElement
    val textBtn = document.getElementById("Text") as HTMLButtonElement

    circleBtn.addEventListener("click", { drawCircle(ctx, startX, startY) })
    rectangleBtn.addEventListener("click", { drawRectangle(ctx) })
    lineBtn.addEventListener("click", { drawLine(ctx) })
    textBtn.addEventListener("click", {
        val text = textInput.value
        drawText(ctx, text)
    })
}
