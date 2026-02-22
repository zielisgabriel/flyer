import "@/css/globals.css"
import { Geist } from "next/font/google"

const geist = Geist({
  weight: ["400", "500", "600", "700", "800", "900"]
})

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="pt-br" className="dark">
      <body
        className={`${geist.className} antialiased`}
      >
        {children}
      </body>
    </html>
  )
}
