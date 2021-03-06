import Core.Chip8Core

import java.awt.Color
import java.awt.geom.Rectangle2D
import javax.swing.Timer
import scala.swing.event.{Key, KeyPressed, KeyReleased}
import scala.swing.{Action, Dimension, Graphics2D, MainFrame, Menu, MenuBar, MenuItem, Panel, SimpleSwingApplication, Swing}

object Chip8 extends SimpleSwingApplication{

  def top = new MainFrame {

    title = "Chip 8"
    val baseWidth = 64
    val baseHeight = 32
    val scale = 8
    val screenDimensions = new Dimension(baseWidth*scale, baseHeight*scale)
    size = screenDimensions
    iconImage = toolkit.getImage("src/main/resources/c8.png")

    val games: Array[String] = Array(
      "15PUZZLE",
      "BLINKY",
      "BLITZ",
      "BRIX",
      "CONNECT4",
      "GUESS",
      "HIDDEN",
      "INVADERS",
      "KALEID",
      "MAZE",
      "MERLIN",
      "MISSILE",
      "PONG",
      "PONG2",
      "PUZZLE",
      "SYZYGY",
      "TANK",
      "TETRIS",
      "TICTAC",
      "UFO",
      "VBRIX",
      "VERS",
      "WIPEOFF"
    )

    val core = new Chip8Core
    core.loadGame("PONG")

    val clock = new Timer(1, Swing.ActionListener(e => {
      core.emulateCycle()
      screen.repaint()
    }))

    menuBar = new MenuBar {
      val gamesMenu = new Menu("Games")
      for (game <- games) {
        gamesMenu.contents += new MenuItem(Action(game) {
          core.initialize()
          core.loadGame(game)
        })
      }
      contents += gamesMenu
      contents += new MenuItem(Action("Pause") {clock.stop()})
      contents += new MenuItem(Action("Resume") {clock.start()})
    }

    val screen = new Panel {
      preferredSize = screenDimensions

      focusable = true
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Key1, _, _) => core.registerKeyPress(0)
        case KeyReleased(_, Key.Key1, _, _) => core.registerKeyRelease(0)

        case KeyPressed(_, Key.Key2, _, _) => core.registerKeyPress(1)
        case KeyReleased(_, Key.Key2, _, _) => core.registerKeyRelease(1)

        case KeyPressed(_, Key.Key3, _, _) => core.registerKeyPress(2)
        case KeyReleased(_, Key.Key3, _, _) => core.registerKeyRelease(2)

        case KeyPressed(_, Key.Key4, _, _) => core.registerKeyPress(3)
        case KeyReleased(_, Key.Key4, _, _) => core.registerKeyRelease(3)

        case KeyPressed(_, Key.Q, _, _) => core.registerKeyPress(4)
        case KeyReleased(_, Key.Q, _, _) => core.registerKeyRelease(4)

        case KeyPressed(_, Key.W, _, _) => core.registerKeyPress(5)
        case KeyReleased(_, Key.W, _, _) => core.registerKeyRelease(5)

        case KeyPressed(_, Key.E, _, _) => core.registerKeyPress(6)
        case KeyReleased(_, Key.E, _, _) => core.registerKeyRelease(6)

        case KeyPressed(_, Key.R, _, _) => core.registerKeyPress(7)
        case KeyReleased(_, Key.R, _, _) => core.registerKeyRelease(7)

        case KeyPressed(_, Key.A, _, _) => core.registerKeyPress(8)
        case KeyReleased(_, Key.A, _, _) => core.registerKeyRelease(8)

        case KeyPressed(_, Key.S, _, _) => core.registerKeyPress(9)
        case KeyReleased(_, Key.S, _, _) => core.registerKeyRelease(9)

        case KeyPressed(_, Key.D, _, _) => core.registerKeyPress(10)
        case KeyReleased(_, Key.D, _, _) => core.registerKeyRelease(10)

        case KeyPressed(_, Key.F, _, _) => core.registerKeyPress(11)
        case KeyReleased(_, Key.F, _, _) => core.registerKeyRelease(11)

        case KeyPressed(_, Key.Y, _, _) => core.registerKeyPress(12)
        case KeyReleased(_, Key.Y, _, _) => core.registerKeyRelease(12)

        case KeyPressed(_, Key.X, _, _) => core.registerKeyPress(13)
        case KeyReleased(_, Key.X, _, _) => core.registerKeyRelease(13)

        case KeyPressed(_, Key.C, _, _) => core.registerKeyPress(14)
        case KeyReleased(_, Key.C, _, _) => core.registerKeyRelease(14)

        case KeyPressed(_, Key.V, _, _) => core.registerKeyPress(15)
        case KeyReleased(_, Key.V, _, _) => core.registerKeyRelease(15)
      }

      override def paint(g: Graphics2D): Unit = {
        g.setPaint(Color.black)
        g.fill(new Rectangle2D.Double(0, 0, preferredSize.width, preferredSize.height))
        g.setPaint(Color.white)

        for ((displayColumn: Array[Int], positionX: Int) <- core.display.zipWithIndex) {
          for ((pixelValue: Int, positionY: Int) <- displayColumn.zipWithIndex) {
            if (pixelValue == 1) {
              g.fill(new Rectangle2D.Double(positionX*scale, positionY*scale, scale, scale))
            }
          }
        }
      }
    }
    contents = screen
    centerOnScreen()
    clock.start()

  }

}
