"use client"

import { signIn } from "next-auth/react"
import { Button } from "./ui/button"

export function Header() {
  return (
    <header className="px-2 py-4 border border-b-border">
      <div className="w-7xl mx-auto flex justify-end items-center">
        <div className="space-x-2">
          <Button
            onClick={() => signIn("ory-hydra")}
          >
            Login
          </Button>

          <Button variant={"outline"}>
            Cadastrar
          </Button>
        </div>
      </div>
    </header>
  )
}