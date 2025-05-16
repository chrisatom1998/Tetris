package com.tetris.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.tetris.model.Board
import com.tetris.model.Piece
import com.tetris.utils.ThemeManager

/**
 * Custom SurfaceView for rendering the Tetris game using Canvas.
 * Handles drawing the board, pieces, and UI elements.
 */
class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    // Game dimensions
    private var boardWidth = 10
    private var boardHeight = 20
    
    // Drawing dimensions
    private var cellSize = 0f
    private var boardRect = RectF()
    private var nextPieceRect = RectF()
    private var holdPieceRect = RectF()
    private var scoreRect = RectF()
    
    // Drawing tools
    private val boardPaint = Paint()
    private val gridPaint = Paint()
    private val blockPaint = Paint()
    private val textPaint = Paint()
    private val shadowPaint = Paint()
    
    // Theme manager
    private val themeManager = ThemeManager.getInstance(context)
    
    // Game state for rendering
    private var board: Board? = null
    private var currentPiece: Piece? = null
    private var nextPiece: Piece? = null
    private var heldPiece: Piece? = null
    private var ghostPieceY = 0
    
    // Render flags
    private var showGridLines = true
    private var showGhostPiece = true
    private var useThemeColors = true
    
    // Game metrics
    private var score = 0L
    private var level = 1
    private var linesCleared = 0
    
    init {
        // Initialize paints
        boardPaint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
        
        gridPaint.apply {
            color = Color.DKGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        
        blockPaint.apply {
            style = Paint.Style.FILL
        }
        
        textPaint.apply {
            color = Color.WHITE
            textSize = 36f
            textAlign = Paint.Align.LEFT
            isFakeBoldText = true
        }
        
        shadowPaint.apply {
            style = Paint.Style.FILL
            color = Color.GRAY
            alpha = 128
        }
        
        // Set up the surface holder
        holder.addCallback(this)
    }
    
    /**
     * Set the dimensions of the game board
     */
    fun setBoardSize(width: Int, height: Int) {
        boardWidth = width
        boardHeight = height
        requestLayout()
    }
    
    /**
     * Set rendering options
     */
    fun setRenderOptions(showGrid: Boolean, showGhost: Boolean, useTheme: Boolean) {
        showGridLines = showGrid
        showGhostPiece = showGhost
        useThemeColors = useTheme
        invalidate()
    }
    
    /**
     * Update the game state for rendering
     */
    fun updateGameState(
        newBoard: Board?,
        newCurrentPiece: Piece?,
        newNextPiece: Piece?,
        newHeldPiece: Piece?
    ) {
        board = newBoard
        currentPiece = newCurrentPiece
        nextPiece = newNextPiece
        heldPiece = newHeldPiece
        
        // Calculate ghost piece position
        calculateGhostPiecePosition()
        
        // Request a redraw
        render()
    }
    
    /**
     * Update game metrics
     */
    fun updateMetrics(newScore: Long, newLevel: Int, newLinesCleared: Int) {
        score = newScore
        level = newLevel
        linesCleared = newLinesCleared
        invalidate()
    }
    
    /**
     * Calculate the position of the ghost piece
     */
    private fun calculateGhostPiecePosition() {
        val piece = currentPiece ?: return
        val gameboard = board ?: return
        
        // Start at current piece position
        ghostPieceY = piece.y
        
        // Move down until collision
        while (ghostPieceY < boardHeight) {
            if (isCollision(piece, piece.x, ghostPieceY + 1, gameboard)) {
                break
            }
            ghostPieceY++
        }
    }
    
    /**
     * Check if a piece would collide at the specified position
     */
    private fun isCollision(piece: Piece, x: Int, y: Int, board: Board): Boolean {
        piece.blocks.forEach { block ->
            val blockX = x + block.x
            val blockY = y + block.y
            
            // Check bounds
            if (blockX < 0 || blockX >= boardWidth || blockY < 0 || blockY >= boardHeight) {
                return true
            }
            
            // Check collision with existing blocks
            if (blockY >= 0 && board.cells[blockY][blockX] != null) {
                return true
            }
        }
        
        return false
    }
    
    /**
     * Render the game state
     */
    fun render() {
        if (!holder.surface.isValid) return
        
        val canvas = holder.lockCanvas() ?: return
        try {
            // Clear the canvas
            canvas.drawColor(themeManager.getBackgroundColor())
            
            // Draw the game elements
            drawBoard(canvas)
            drawCurrentPiece(canvas)
            if (showGhostPiece) {
                drawGhostPiece(canvas)
            }
            drawNextPiece(canvas)
            drawHeldPiece(canvas)
            drawMetrics(canvas)
        } finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }
    
    /**
     * Draw the game board
     */
    private fun drawBoard(canvas: Canvas) {
        // Draw board background
        canvas.drawRect(boardRect, boardPaint)
        
        // Draw placed blocks
        val gameboard = board ?: return
        for (y in 0 until boardHeight) {
            for (x in 0 until boardWidth) {
                val color = gameboard.cells[y][x]
                if (color != null) {
                    drawBlock(canvas, x, y, color)
                }
            }
        }
        
        // Draw grid lines if enabled
        if (showGridLines) {
            drawGrid(canvas)
        }
    }
    
    /**
     * Draw a grid on the board
     */
    private fun drawGrid(canvas: Canvas) {
        // Draw horizontal grid lines
        for (y in 0..boardHeight) {
            val yPos = boardRect.top + y * cellSize
            canvas.drawLine(boardRect.left, yPos, boardRect.right, yPos, gridPaint)
        }
        
        // Draw vertical grid lines
        for (x in 0..boardWidth) {
            val xPos = boardRect.left + x * cellSize
            canvas.drawLine(xPos, boardRect.top, xPos, boardRect.bottom, gridPaint)
        }
    }
    
    /**
     * Draw the current active piece
     */
    private fun drawCurrentPiece(canvas: Canvas) {
        val piece = currentPiece ?: return
        
        piece.blocks.forEach { block ->
            val x = piece.x + block.x
            val y = piece.y + block.y
            
            // Only draw blocks that are within the visible board area
            if (y >= 0) {
                drawBlock(canvas, x, y, piece.color)
            }
        }
    }
    
    /**
     * Draw the ghost piece (preview of where the current piece will land)
     */
    private fun drawGhostPiece(canvas: Canvas) {
        val piece = currentPiece ?: return
        
        // Set ghost piece semi-transparent
        shadowPaint.color = piece.color
        shadowPaint.alpha = 80
        
        piece.blocks.forEach { block ->
            val x = piece.x + block.x
            val y = ghostPieceY + block.y
            
            // Only draw blocks that are within the visible board area and below the current piece
            if (y >= 0 && y >= piece.y + block.y) {
                val left = boardRect.left + x * cellSize
                val top = boardRect.top + y * cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                
                canvas.drawRect(left + 1, top + 1, right - 1, bottom - 1, shadowPaint)
            }
        }
    }
    
    /**
     * Draw the next piece preview
     */
    private fun drawNextPiece(canvas: Canvas) {
        // Draw next piece box
        canvas.drawRect(nextPieceRect, boardPaint)
        canvas.drawRect(nextPieceRect, gridPaint)
        
        // Draw "NEXT" label
        val textX = nextPieceRect.left + 10
        val textY = nextPieceRect.top - 10
        canvas.drawText("NEXT", textX, textY, textPaint)
        
        // Draw the next piece
        val piece = nextPiece ?: return
        val previewCellSize = cellSize * 0.8f
        
        // Calculate center position for the preview
        val centerX = nextPieceRect.left + nextPieceRect.width() / 2
        val centerY = nextPieceRect.top + nextPieceRect.height() / 2
        
        // Calculate the bounds of the piece for centering
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        
        piece.blocks.forEach { block ->
            minX = minOf(minX, block.x)
            maxX = maxOf(maxX, block.x)
            minY = minOf(minY, block.y)
            maxY = maxOf(maxY, block.y)
        }
        
        val pieceWidth = maxX - minX + 1
        val pieceHeight = maxY - minY + 1
        val offsetX = centerX - (pieceWidth * previewCellSize) / 2
        val offsetY = centerY - (pieceHeight * previewCellSize) / 2
        
        // Draw the blocks
        blockPaint.color = piece.color
        piece.blocks.forEach { block ->
            val left = offsetX + (block.x - minX) * previewCellSize
            val top = offsetY + (block.y - minY) * previewCellSize
            val right = left + previewCellSize
            val bottom = top + previewCellSize
            
            canvas.drawRect(left + 1, top + 1, right - 1, bottom - 1, blockPaint)
        }
    }
    
    /**
     * Draw the held piece preview
     */
    private fun drawHeldPiece(canvas: Canvas) {
        // Draw held piece box
        canvas.drawRect(holdPieceRect, boardPaint)
        canvas.drawRect(holdPieceRect, gridPaint)
        
        // Draw "HOLD" label
        val textX = holdPieceRect.left + 10
        val textY = holdPieceRect.top - 10
        canvas.drawText("HOLD", textX, textY, textPaint)
        
        // Draw the held piece if there is one
        val piece = heldPiece ?: return
        val previewCellSize = cellSize * 0.8f
        
        // Calculate center position for the preview
        val centerX = holdPieceRect.left + holdPieceRect.width() / 2
        val centerY = holdPieceRect.top + holdPieceRect.height() / 2
        
        // Calculate the bounds of the piece for centering
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        
        piece.blocks.forEach { block ->
            minX = minOf(minX, block.x)
            maxX = maxOf(maxX, block.x)
            minY = minOf(minY, block.y)
            maxY = maxOf(maxY, block.y)
        }
        
        val pieceWidth = maxX - minX + 1
        val pieceHeight = maxY - minY + 1
        val offsetX = centerX - (pieceWidth * previewCellSize) / 2
        val offsetY = centerY - (pieceHeight * previewCellSize) / 2
        
        // Draw the blocks
        blockPaint.color = piece.color
        piece.blocks.forEach { block ->
            val left = offsetX + (block.x - minX) * previewCellSize
            val top = offsetY + (block.y - minY) * previewCellSize
            val right = left + previewCellSize
            val bottom = top + previewCellSize
            
            canvas.drawRect(left + 1, top + 1, right - 1, bottom - 1, blockPaint)
        }
    }
    
    /**
     * Draw the game metrics (score, level, lines)
     */
    private fun drawMetrics(canvas: Canvas) {
        // Draw metrics background
        canvas.drawRect(scoreRect, boardPaint)
        canvas.drawRect(scoreRect, gridPaint)
        
        // Draw labels and values
        val textX = scoreRect.left + 10
        val lineHeight = textPaint.textSize * 1.5f
        
        var textY = scoreRect.top + lineHeight
        canvas.drawText("SCORE", textX, textY, textPaint)
        textY += textPaint.textSize
        textPaint.textSize = 28f
        canvas.drawText(score.toString(), textX, textY, textPaint)
        textPaint.textSize = 36f
        
        textY += lineHeight
        canvas.drawText("LEVEL", textX, textY, textPaint)
        textY += textPaint.textSize
        textPaint.textSize = 28f
        canvas.drawText(level.toString(), textX, textY, textPaint)
        textPaint.textSize = 36f
        
        textY += lineHeight
        canvas.drawText("LINES", textX, textY, textPaint)
        textY += textPaint.textSize
        textPaint.textSize = 28f
        canvas.drawText(linesCleared.toString(), textX, textY, textPaint)
        textPaint.textSize = 36f
    }
    
    /**
     * Draw a single block at the specified grid position
     */
    private fun drawBlock(canvas: Canvas, x: Int, y: Int, blockColor: Int) {
        // Only draw blocks within the board boundaries
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) return
        
        blockPaint.color = blockColor
        
        val left = boardRect.left + x * cellSize
        val top = boardRect.top + y * cellSize
        val right = left + cellSize
        val bottom = top + cellSize
        
        // Draw the main block
        canvas.drawRect(left + 1, top + 1, right - 1, bottom - 1, blockPaint)
        
        // Draw highlights and shadows for 3D effect
        val highlightPaint = Paint(blockPaint)
        val shadowPaint = Paint(blockPaint)
        
        // Make highlight brighter
        val r = Color.red(blockColor)
        val g = Color.green(blockColor)
        val b = Color.blue(blockColor)
        highlightPaint.color = Color.rgb(
            Math.min(r + 60, 255),
            Math.min(g + 60, 255),
            Math.min(b + 60, 255)
        )
        
        // Make shadow darker
        shadowPaint.color = Color.rgb(
            Math.max(r - 60, 0),
            Math.max(g - 60, 0),
            Math.max(b - 60, 0)
        )
        
        // Draw highlight on top and left
        canvas.drawRect(left + 1, top + 1, right - 3, top + 3, highlightPaint)
        canvas.drawRect(left + 1, top + 3, left + 3, bottom - 3, highlightPaint)
        
        // Draw shadow on bottom and right
        canvas.drawRect(left + 3, bottom - 3, right - 1, bottom - 1, shadowPaint)
        canvas.drawRect(right - 3, top + 3, right - 1, bottom - 1, shadowPaint)
    }
    
    /**
     * Calculate the layout of game elements based on the view size
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        
        // Calculate cell size based on available space
        // Board takes 65% of width, sidebar takes 35%
        val boardWidth = w * 0.65f
        cellSize = boardWidth / this.boardWidth
        
        // Board rect (centered vertically)
        val boardActualHeight = cellSize * boardHeight
        val boardTop = (h - boardActualHeight) / 2
        boardRect.set(0f, boardTop, boardWidth, boardTop + boardActualHeight)
        
        // Sidebar layout
        val sidebarLeft = boardWidth + 20
        val sidebarWidth = w - sidebarLeft - 10
        
        // Next piece preview (20% of height)
        val nextPieceSize = minOf(sidebarWidth, h * 0.2f)
        nextPieceRect.set(
            sidebarLeft,
            boardTop,
            sidebarLeft + nextPieceSize,
            boardTop + nextPieceSize
        )
        
        // Held piece preview (20% of height)
        holdPieceRect.set(
            sidebarLeft,
            nextPieceRect.bottom + 20,
            sidebarLeft + nextPieceSize,
            nextPieceRect.bottom + 20 + nextPieceSize
        )
        
        // Score display (remaining space)
        scoreRect.set(
            sidebarLeft,
            holdPieceRect.bottom + 20,
            sidebarLeft + sidebarWidth,
            (h - 10).toFloat()
        )
        
        // Adjust text size based on view dimensions
        val baseFontSize = w / 30f
        textPaint.textSize = baseFontSize
    }
    
    /**
     * Surface created callback
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        render()
    }
    
    /**
     * Surface changed callback
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        render()
    }
    
    /**
     * Surface destroyed callback
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Nothing to do here
    }
}