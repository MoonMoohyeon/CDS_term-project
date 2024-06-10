import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.MouseEvent
import kotlin.math.PI
import kotlin.math.abs

// Canvas 요소와 컨텍스트 가져오기
val canvas = document.getElementById("Canvas") as HTMLCanvasElement
val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

// 현재 그려진 도형들을 저장할 리스트
val shapes = mutableListOf<Shape>()
val drawingshapes = mutableListOf<Shape>()

// 도형 정보를 저장할 데이터 클래스 정의
sealed class Shape {
    data class Circle(val lineWidth: Int, val strokeColor: String, val fillColor: String, val centerX: Double, val centerY: Double, val radius: Double) : Shape()
    data class Rectangle(val lineWidth: Int, val strokeColor: String, val fillColor: String, val x: Double, val y: Double, val width: Double, val height: Double) : Shape()
    data class Line(val lineWidth: Int, val strokeColor: String, val x1: Double, val y1: Double, val x2: Double, val y2: Double) : Shape()
    data class Text(val lineWidth: Int, val strokeColor: String, val fillColor: String, val x: Double, val y: Double, val down: Double, val text: String) : Shape()
}

// 저장된 도형들을 다시 그리는 함수
fun redrawShapes() {
    ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    for (shape in shapes) {
        when (shape) {
            is Shape.Circle -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.beginPath()
                ctx.ellipse(shape.centerX, shape.centerY, shape.radius, shape.radius, 0.0, 0.0, 2 * PI)
                ctx.closePath()
                ctx.stroke()
                ctx.fill()
            }
            is Shape.Rectangle -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.beginPath()
                ctx.rect(shape.x, shape.y, shape.width, shape.height)
                ctx.closePath()
                ctx.stroke()
                ctx.fill()
            }
            is Shape.Line -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.beginPath()
                ctx.moveTo(shape.x1, shape.y1)
                ctx.lineTo(shape.x2, shape.y2)
                ctx.closePath()
                ctx.stroke()
            }
            is Shape.Text -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.font = "20px Arial"
                ctx.fillText(shape.text, shape.x, shape.y, shape.down)
                ctx.strokeText(shape.text, shape.x, shape.y, shape.down)
            }
        }
    }
}

fun receivedShapes() {
    ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    for (shape in drawingshapes) {
        when (shape) {
            is Shape.Circle -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.beginPath()
                ctx.ellipse(shape.centerX, shape.centerY, shape.radius, shape.radius, 0.0, 0.0, 2 * PI)
                ctx.closePath()
                ctx.stroke()
                ctx.fill()
            }
            is Shape.Rectangle -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.beginPath()
                ctx.rect(shape.x, shape.y, shape.width, shape.height)
                ctx.closePath()
                ctx.stroke()
                ctx.fill()
            }
            is Shape.Line -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.beginPath()
                ctx.moveTo(shape.x1, shape.y1)
                ctx.lineTo(shape.x2, shape.y2)
                ctx.closePath()
                ctx.stroke()
            }
            is Shape.Text -> {
                ctx.lineWidth = shape.lineWidth.toDouble()
                ctx.strokeStyle = shape.strokeColor
                ctx.fillStyle = shape.fillColor
                ctx.font = "20px Arial"
                ctx.fillText(shape.text, shape.x, shape.y, shape.down)
                ctx.strokeText(shape.text, shape.x, shape.y, shape.down)
            }
        }
    }
}

fun main() {
    // 기본 값 설정
    var isDrawing = false
    var lineWidth = 2
    var strokeColor = "#000000"
    var fillColor = "#000000"
    var downX = 0.0
    var downY = 0.0
    var upX = 0.0
    var upY = 0.0
    var currentX = 0.0 // 원
    var currentY = 0.0 // 원
    var subX = 0.0 // 사각형 가로
    var subY = 0.0 // 사각형 세로

    // 텍스트 박스
    val textInput = document.getElementById("TextInput") as HTMLInputElement

    // 웹소켓
    val webSocket = WebSocket("ws://localhost:9090")
    fun connect() {
        webSocket.onopen = {
            val connectFrame = "CONNECT\n\n\u0000"
            webSocket.send(connectFrame)
        }

        webSocket.onmessage = { event ->
            val receiveMessage = (event as MessageEvent).data as String
            println("Received message: $receiveMessage")

            val tokens = receiveMessage.split(" ")
            val type = tokens[0]

            when (type) {
                "circle" -> receiveCircle(tokens)
                "circledrawing" -> receivedrawingCircle(tokens)
                "rectangle" -> receiveRectangle(tokens)
                "rectangledrawing" -> receivedrawingRectangle(tokens)
                "line" -> receiveLine(tokens)
                "linedrawing" -> receivedrawingLine(tokens)
                "text" -> receiveText(tokens)
            }
        }

        webSocket.onerror = { errorEvent ->
            val error = errorEvent.asDynamic().error as String
            println("WebSocket error: $error")
        }

        // 연결을 시도하는데 실패했을 때
        webSocket.onclose = {
            println("WebSocket connection failed")
        }

        window.onbeforeunload = {
            webSocket.close()
            null
        }
    }

    fun sendMessage(message: String) {
        println(message)
        webSocket.send(message)
    }

    fun disconnect() {
        val disconnectFrame = "DISCONNECT\n\n\u0000"
        webSocket.send(disconnectFrame)
    }

    connect()

    // 리스너 정의
    // 원 그리는 이벤트리스너
    val circleListener = EventListener { event ->
        when (event.type) {
            "mousedown" -> {
                isDrawing = true
                downX = (event as MouseEvent).offsetX
                downY = event.offsetY
            }
            "mousemove" -> {
                if (isDrawing) {
                    currentX = (event as MouseEvent).offsetX
                    currentY = event.offsetY

                    val centerX = (currentX + downX) / 2
                    val centerY = (currentY + downY) / 2
                    val radius = if (abs(currentX - downX) < abs(currentY - downY)) {
                        abs(currentX - downX) / 2
                    } else {
                        abs(currentY - downY) / 2
                    }

                    redrawShapes()
                    ctx.beginPath()
                    ctx.ellipse(centerX, centerY, radius, radius, 0.0, 0.0, 2 * PI)
                    ctx.closePath()
                    ctx.stroke()
                    ctx.fill()

                    sendMessage("circledrawing $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
                }
            }
            "mouseup" -> {
                isDrawing = false
                upX = (event as MouseEvent).offsetX
                upY = event.offsetY

                val centerX = (upX + downX) / 2
                val centerY = (upY + downY) / 2
                val radius = if (abs(upX - downX) < abs(upY - downY)) {
                    abs(upX - downX) / 2
                } else {
                    abs(upY - downY) / 2
                }

                shapes.add(Shape.Circle(lineWidth, strokeColor, fillColor, centerX, centerY, radius))
                redrawShapes()
                sendMessage("circle $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            }
        }
    }

    // 사각형 그리는 이벤트리스너
    val rectangleListener = EventListener { event ->
        when (event.type) {
            "mousedown" -> {
                isDrawing = true
                downX = (event as MouseEvent).offsetX
                downY = event.offsetY
            }
            "mousemove" -> {
                if (isDrawing) {
                    currentX = (event as MouseEvent).offsetX
                    currentY = event.offsetY
                    subX = currentX - downX
                    subY = currentY - downY

                    redrawShapes()
                    ctx.lineWidth = lineWidth.toDouble()
                    ctx.strokeStyle = strokeColor
                    ctx.fillStyle = fillColor
                    ctx.beginPath()
                    ctx.rect(downX, downY, subX, subY)
                    ctx.closePath()
                    ctx.stroke()
                    ctx.fill()

                    sendMessage("rectangledrawing $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
                }
            }
            "mouseup" -> {
                isDrawing = false
                upX = (event as MouseEvent).offsetX
                upY = event.offsetY
                subX = upX - downX
                subY = upY - downY

                shapes.add(Shape.Rectangle(lineWidth, strokeColor, fillColor, downX, downY, subX, subY))
                redrawShapes()
                sendMessage("rectangle $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            }
        }
    }

    // 선 그리는 이벤트리스너
    val lineListener = EventListener { event ->
        when (event.type) {
            "mousedown" -> {
                isDrawing = true
                downX = (event as MouseEvent).offsetX
                downY = event.offsetY
            }
            "mousemove" -> {
                if (isDrawing) {
                    currentX = (event as MouseEvent).offsetX
                    currentY = event.offsetY

                    redrawShapes()
                    ctx.beginPath()
                    ctx.moveTo(downX, downY)
                    ctx.lineTo(currentX, currentY)
                    ctx.closePath()
                    ctx.stroke()

                    sendMessage("linedrawing $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
                }
            }
            "mouseup" -> {
                isDrawing = false
                upX = (event as MouseEvent).offsetX
                upY = event.offsetY

                shapes.add(Shape.Line(lineWidth, strokeColor, downX, downY, upX, upY))
                redrawShapes()
                sendMessage("line $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            }
        }
    }

    // 텍스트 이벤트리스너
    val textListener = EventListener { event ->
        val upX = event.asDynamic().offsetX.toString().toDouble()
        val upY = event.asDynamic().offsetY.toString().toDouble()
        val subY = abs(upY - downY)
        val text = textInput.value
        ctx.font = "20px Arial"
        ctx.fillStyle = fillColor
        ctx.strokeStyle = strokeColor
        ctx.fillText(text, downX, downY, subY)
        ctx.strokeText(text, downX, downY, subY)

        shapes.add(Shape.Text(lineWidth, strokeColor, fillColor, downX, downY, subY, text))
        redrawShapes()
        sendMessage("text "+lineWidth+" "+strokeColor+" "+fillColor+" "+downX+" "+downY+" "+subY+" "+text)
    }

    // 버튼에 리스너 붙이기
    fun drawCircle() {
        canvas.removeEventListener("mousedown", rectangleListener)
        canvas.removeEventListener("mousedown", lineListener)
        canvas.removeEventListener("mousedown", textListener)
        canvas.removeEventListener("mousemove", rectangleListener)
        canvas.removeEventListener("mousemove", lineListener)
        canvas.removeEventListener("mousemove", textListener)
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mousedown", circleListener)
        canvas.addEventListener("mousemove", circleListener)
        canvas.addEventListener("mouseup", circleListener)
    }

    fun drawRectangle() {
        canvas.removeEventListener("mousedown", circleListener)
        canvas.removeEventListener("mousedown", lineListener)
        canvas.removeEventListener("mousedown", textListener)
        canvas.removeEventListener("mousemove", circleListener)
        canvas.removeEventListener("mousemove", lineListener)
        canvas.removeEventListener("mousemove", textListener)
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mousedown", rectangleListener)
        canvas.addEventListener("mousemove", rectangleListener)
        canvas.addEventListener("mouseup", rectangleListener)
    }

    fun drawLine() {
        canvas.removeEventListener("mousedown", circleListener)
        canvas.removeEventListener("mousedown", rectangleListener)
        canvas.removeEventListener("mousedown", textListener)
        canvas.removeEventListener("mousemove", circleListener)
        canvas.removeEventListener("mousemove", rectangleListener)
        canvas.removeEventListener("mousemove", textListener)
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", textListener)
        canvas.addEventListener("mousedown", lineListener)
        canvas.addEventListener("mousemove", lineListener)
        canvas.addEventListener("mouseup", lineListener)
    }

    fun drawText() {
        canvas.removeEventListener("mousedown", circleListener)
        canvas.removeEventListener("mousedown", rectangleListener)
        canvas.removeEventListener("mousedown", lineListener)
        canvas.removeEventListener("mousemove", circleListener)
        canvas.removeEventListener("mousemove", rectangleListener)
        canvas.removeEventListener("mousemove", lineListener)
        canvas.removeEventListener("mouseup", circleListener)
        canvas.removeEventListener("mouseup", rectangleListener)
        canvas.removeEventListener("mouseup", lineListener)
        canvas.addEventListener("mousedown", textListener)
//        canvas.addEventListener("mousemove", textListener)
        canvas.addEventListener("mouseup", textListener)
    }

    // 마우스 다운 이벤트 처리
    canvas.addEventListener("mousedown", { event ->
        isDrawing = true
        downX = (event as MouseEvent).offsetX
        downY = event.offsetY
        currentX = event.offsetX
        currentY = event.offsetY
        ctx.fillStyle = fillColor
    })

    // 마우스 이동 이벤트 처리
    canvas.addEventListener("mousemove", { event ->
        if (!isDrawing) return@addEventListener
        currentX = (event as MouseEvent).offsetX
        currentY = event.offsetY
        subX = currentX - downX // 가로
        subY = currentY - downY // 세로
    })

    // 마우스 업 이벤트 처리
    canvas.addEventListener("mouseup", { event ->
        isDrawing = false
        upX = (event as MouseEvent).offsetX
        upY = event.offsetY
        currentX = event.offsetX
        currentY = event.offsetY
        subX = currentX - downX // 가로
        subY = currentY - downY // 세로
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

    // fill 버튼 클릭 이벤트 처리
    val fillColorBtn = document.getElementById("fill") as HTMLButtonElement
    fillColorBtn.addEventListener("click", {
        ctx.fillStyle = fillColorInput.value
        fillColor = fillColorInput.value
    })

    // transparent 버튼 클릭 이벤트 처리
    val transparentBtn = document.getElementById("transparent") as HTMLButtonElement
    transparentBtn.addEventListener("click", {
        ctx.fillStyle = "transparent"
        fillColor = "transparent"
    })

    // 도형 버튼 이벤트 처리
    val circleBtn = document.getElementById("Circle") as HTMLButtonElement
    val rectangleBtn = document.getElementById("Rectangle") as HTMLButtonElement
    val lineBtn = document.getElementById("Line") as HTMLButtonElement
    val textBtn = document.getElementById("Text") as HTMLButtonElement

    circleBtn.addEventListener("click", { drawCircle() })
    rectangleBtn.addEventListener("click", { drawRectangle() })
    lineBtn.addEventListener("click", { drawLine() })
    textBtn.addEventListener("click", { drawText() })
}

fun receiveCircle(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    val centerX = (upX + downX) / 2
    val centerY = (upY + downY) / 2
    val radiusX = abs((upX - downX) / 2)
    val radiusY = abs((upY - downY) / 2)

    shapes.add(Shape.Circle(lineWidth, strokeStyle, fillStyle, centerX, centerY, radiusX))
    redrawShapes()
}

fun receivedrawingCircle(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    val centerX = (upX + downX) / 2
    val centerY = (upY + downY) / 2
    val radiusX = abs((upX - downX) / 2)
    val radiusY = abs((upY - downY) / 2)

    drawingshapes.add(Shape.Circle(lineWidth, strokeStyle, fillStyle, centerX, centerY, radiusX))
    receivedShapes()
}

fun receiveRectangle(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    val subX = abs(upX - downX) // 가로
    val subY = abs(upY - downY) // 세로

    shapes.add(Shape.Rectangle(lineWidth, strokeStyle, fillStyle, downX, downY, subX, subY))
    redrawShapes()
}

fun receivedrawingRectangle(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    val subX = abs(upX - downX) // 가로
    val subY = abs(upY - downY) // 세로

    drawingshapes.add(Shape.Rectangle(lineWidth, strokeStyle, fillStyle, downX, downY, subX, subY))
    receivedShapes()
}

fun receiveLine(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    shapes.add(Shape.Line(lineWidth, strokeStyle, downX, downY, upX, upY))
    redrawShapes()
}

fun receivedrawingLine(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val upX = message[4].toDouble()
    val upY = message[5].toDouble()
    val downX = message[6].toDouble()
    val downY = message[7].toDouble()

    drawingshapes.add(Shape.Line(lineWidth, strokeStyle, downX, downY, upX, upY))
    receivedShapes()
}

fun receiveText(message: List<String>) {
    val lineWidth = message[1].toInt()
    val strokeStyle = message[2]
    val fillStyle = message[3]
    val downX = message[4].toDouble()
    val downY = message[5].toDouble()
    val subY = message[6].toDouble()
    val text = message[7]

    shapes.add(Shape.Text(lineWidth, strokeStyle, fillStyle, downX, downY, subY, text))
    redrawShapes()
}
