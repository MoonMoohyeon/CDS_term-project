import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import kotlin.math.PI
import kotlin.math.abs
//import kotlin.browser.document

// Canvas 요소와 컨텍스트 가져오기
val canvas = document.getElementById("Canvas") as HTMLCanvasElement
val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

fun main() {

    // 기본 값 설정
    var isDrawing = false
    var lineWidth = 2
    var strokeColor = "#000000"
    var fillColor = "#000000"
    var downX = 0.0
    var downY = 0.0

    //텍스트 박스
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

//            val type = receiveMessage.get(0)
            val tokens = receiveMessage.split(" ")
            var type = tokens.get(0)

            if(type.equals("circle")) {
                receiveCircle(tokens)
            }
            else if(type.equals("rectangle")) {
                receiveRectangle(tokens)
            }
            else if(type.equals("line")) {
                receiveLine(tokens)
            }
            else if( type.equals("text")) {
                receiveText(tokens)
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
        if (event.type == "mousedown") {
            downX = event.asDynamic().offsetX.toString().toDouble()
            downY = event.asDynamic().offsetY.toString().toDouble()
            isDrawing = true
        }

        if (event.type == "mousemove" && isDrawing) {
            val currentX = event.asDynamic().offsetX.toString().toDouble()
            val currentY = event.asDynamic().offsetY.toString().toDouble()
            val centerX = (currentX + downX) / 2
            val centerY = (currentY + downY) / 2
            val radius = abs(currentX - downX) / 2

            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            ctx.beginPath()
            ctx.ellipse(centerX, centerY, radius, radius, 0.0, 0.0, 2 * PI)
            ctx.stroke()
            ctx.fill()

            sendMessage("circle $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
        }

        if (event.type == "mouseup") {
            val upX = event.asDynamic().offsetX.toString().toDouble()
            val upY = event.asDynamic().offsetY.toString().toDouble()
            val centerX = (upX + downX) / 2
            val centerY = (upY + downY) / 2
            val radius = abs(upX - downX) / 2

            ctx.beginPath()
            ctx.ellipse(centerX, centerY, radius, radius, 0.0, 0.0, 2 * PI)
            ctx.closePath()
            ctx.stroke()
            ctx.fill()

            sendMessage("circle $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            isDrawing = false
        }
    }

    // 사각형 그리는 이벤트리스너
    // Variables to track the initial point and current mouse position
    val rectangleListener = EventListener { event ->
        if (event.type == "mousedown") {
            downX = event.asDynamic().offsetX.toString().toDouble()
            downY = event.asDynamic().offsetY.toString().toDouble()
            isDrawing = true
        }

        if (event.type == "mousemove" && isDrawing) {
            val currentX = event.asDynamic().offsetX.toString().toDouble()
            val currentY = event.asDynamic().offsetY.toString().toDouble()
            val subX = abs(currentX - downX) // 가로
            val subY = abs(currentY - downY) // 세로

            // Clear previous drawing
            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())

            // Redraw the rectangle
            ctx.lineWidth = lineWidth.toDouble()
            ctx.strokeStyle = strokeColor
            ctx.fillStyle = fillColor
            ctx.beginPath()
            ctx.rect(downX, downY, subX, subY)
            ctx.closePath()
            ctx.stroke()
            ctx.fill()

            // Send the current drawing state
            sendMessage("rectangle $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
        }

        if (event.type == "mouseup") {
            val upX = event.asDynamic().offsetX.toString().toDouble()
            val upY = event.asDynamic().offsetY.toString().toDouble()
            val subX = abs(upX - downX) // 가로
            val subY = abs(upY - downY) // 세로

            // Finalize the rectangle
            ctx.lineWidth = lineWidth.toDouble()
            ctx.strokeStyle = strokeColor
            ctx.fillStyle = fillColor
            ctx.beginPath()
            ctx.rect(downX, downY, subX, subY)
            ctx.closePath()
            ctx.stroke()
            ctx.fill()

            // Send the final state
            sendMessage("rectangle $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            isDrawing = false
        }
    }

    // 선 그리는 이벤트리스너
    val lineListener = EventListener { event ->
        if (event.type == "mousedown") {
            downX = event.asDynamic().offsetX.toString().toDouble()
            downY = event.asDynamic().offsetY.toString().toDouble()
            isDrawing = true
        }

        if (event.type == "mousemove" && isDrawing) {
            val currentX = event.asDynamic().offsetX.toString().toDouble()
            val currentY = event.asDynamic().offsetY.toString().toDouble()

            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            ctx.beginPath()
            ctx.moveTo(downX, downY)
            ctx.lineTo(currentX, currentY)
            ctx.closePath()
            ctx.stroke()

            sendMessage("line $lineWidth $strokeColor $fillColor $currentX $currentY $downX $downY")
        }

        if (event.type == "mouseup") {
            val upX = event.asDynamic().offsetX.toString().toDouble()
            val upY = event.asDynamic().offsetY.toString().toDouble()

            ctx.beginPath()
            ctx.moveTo(downX, downY)
            ctx.lineTo(upX, upY)
            ctx.closePath()
            ctx.stroke()

            sendMessage("line $lineWidth $strokeColor $fillColor $upX $upY $downX $downY")
            isDrawing = false
        }
    }

    // 텍스트 이벤트리스너
    val textListener = EventListener { event ->
        if (event.type == "mousedown") {
            downX = event.asDynamic().offsetX.toString().toDouble()
            downY = event.asDynamic().offsetY.toString().toDouble()
            isDrawing = true
        }

        if (event.type == "mouseup" && isDrawing) {
            val upX = event.asDynamic().offsetX.toString().toDouble()
            val upY = event.asDynamic().offsetY.toString().toDouble()
            val subY = abs(upY - downY)
            val text = textInput.value

            ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            ctx.font = "20px Arial"
            ctx.fillText(text, downX, downY, subY)
            ctx.strokeText(text, downX, downY, subY)

            sendMessage("text $lineWidth $strokeColor $fillColor $upX $upY $downX $downY $text")
            isDrawing = false
        }
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
        canvas.addEventListener("mousemove", textListener)
        canvas.addEventListener("mouseup", textListener)
    }

    // 마우스 다운 이벤트 처리
    canvas.addEventListener("mousedown", { event ->
        isDrawing = true
        downX = event.asDynamic().offsetX.toString().toDouble()
        downY = event.asDynamic().offsetY.toString().toDouble()
        ctx.fillStyle = fillColor
    })

//     마우스 이동 이벤트 처리
    canvas.addEventListener("mousemove", { event ->
        if (!isDrawing) return@addEventListener
        val nowX = event.asDynamic().offsetX
        val nowY = event.asDynamic().offsetY
    })

//     마우스 업 이벤트 처리
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

    // fill 버튼 클릭 이벤트 처리
    val fillColorBtn = document.getElementById("fill") as HTMLButtonElement
    fillColorBtn.addEventListener("click", {
        ctx.fillStyle = fillColorInput.value
        fillColor = fillColorInput.value
    })

    //transparent 버튼 클릭 이벤트 처리
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
    val lineWidth = message.get(1)
    val strokeStyle = message.get(2)
    val fillStyle = message.get(3)
    val upX = message.get(4).toDouble()
    val upY = message.get(5).toDouble()
    val downX = message.get(6).toDouble()
    val downY = message.get(7).toDouble()

    ctx.lineWidth = lineWidth.toDouble()
    ctx.fillStyle = fillStyle
    ctx.strokeStyle = strokeStyle

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

fun receiveRectangle(message: List<String>) {
    val lineWidth = message.get(1)
    val strokeStyle = message.get(2)
    val fillStyle = message.get(3)
    val upX = message.get(4).toDouble()
    val upY = message.get(5).toDouble()
    val downX = message.get(6).toDouble()
    val downY = message.get(7).toDouble()

    ctx.lineWidth = lineWidth.toDouble()
    ctx.fillStyle = fillStyle
    ctx.strokeStyle = strokeStyle

    val subX = abs(upX - downX) //가로
    val subY = abs(upY - downY) //세로

    ctx.beginPath()
    ctx.rect(downX, downY, subX, subY)
    ctx.closePath()
    ctx.stroke()
    ctx.fill()
}

fun receiveLine(message: List<String>) {
    val lineWidth = message.get(1)
    val strokeStyle = message.get(2)
    val fillStyle = message.get(3)
    val upX = message.get(4).toDouble()
    val upY = message.get(5).toDouble()
    val downX = message.get(6).toDouble()
    val downY = message.get(7).toDouble()
    ctx.lineWidth = lineWidth.toDouble()
    ctx.fillStyle = fillStyle
    ctx.strokeStyle = strokeStyle

    ctx.beginPath()
    ctx.moveTo(downX, downY)
    ctx.lineTo(upX, upY)
    ctx.closePath()
    ctx.stroke()
}

fun receiveText(message: List<String>) {
    val lineWidth = message.get(1)
    val strokeStyle = message.get(2)
    val fillStyle = message.get(3)
    val upX = message.get(4).toDouble()
    val upY = message.get(5).toDouble()
    val downX = message.get(6).toDouble()
    val downY = message.get(7).toDouble()
    val text = message.get(8)
    ctx.font = "20px Arial"
    ctx.fillStyle = fillStyle
    ctx.strokeStyle = strokeStyle

    val subY = abs(upY - downY)
    ctx.fillText(text, downX, downY, subY)
    ctx.strokeText(text, downX, downY, subY)
}