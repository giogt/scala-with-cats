package giogt

package object cats {
  implicit val printerInstance: printer.Printer = printer.ConsolePrinter()
}
