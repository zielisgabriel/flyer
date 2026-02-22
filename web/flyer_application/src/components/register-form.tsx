"use client"

import { ory } from "@/lib/ory"
import { redirect, useSearchParams } from "next/navigation"
import { useEffect, useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { Spinner } from "./ui/spinner"
import { Label } from "./ui/label"
import { Input } from "./ui/input"
import { Button } from "./ui/button"
import { Separator } from "./ui/separator"
import Link from "next/link"
import { RegistrationFlow, UiNodeInputAttributes } from "@ory/client"
import { getTranslate } from "@/utils/auth-form-translate"

export function RegisterForm() {
  const searchParams = useSearchParams()
  const flowId = searchParams.get("flow")
  const [flow, setFlow] = useState<RegistrationFlow | null>(null)

  useEffect(() => {
    if (flowId) {
      ory.getRegistrationFlow({ id: String(flowId) }, { params: { method: "password" } })
        .then(({ data }) => {
          console.log(data)
          setFlow(data)
        })
        .catch((err) => console.error("Erro ao buscar o fluxo:", err))
    } else {
      redirect("http://localhost:4433/self-service/registration/browser")
    }
  }, [flowId])

  if (!flow) return (
    <div>
      <Card>
        <CardContent>
          <Spinner />
        </CardContent>
      </Card>
    </div>
  )

  return (
    <Card>
      <CardHeader>
        <CardTitle className="font-bold text-2xl">
          Cadastrar
        </CardTitle>
      </CardHeader>

      <CardContent>
        <form
          action={flow.ui.action}
          method={flow.ui.method}
          className="space-y-4"
        >
          {flow.ui.nodes
            .filter((node) => node.attributes.node_type === "input")
            .map((node) => {
              const attrs = node.attributes as UiNodeInputAttributes

              if (attrs.type === "hidden") {
                return (
                  <input
                    key={attrs.name}
                    type="hidden"
                    name={attrs.name}
                    value={attrs.value as string}
                  />
                )
              }

              if (attrs.type === "submit") {
                return (
                  <Button key={attrs.name} className="w-full font-bold" type="submit" name={attrs.name} value={attrs.value}>
                    {getTranslate(node.meta.label?.text)}
                  </Button>
                )
              }

              return (
                <div key={attrs.name} className="space-y-1 mb-4">
                  {node.meta.label && <Label htmlFor={attrs.name}>{node.meta.label.text}</Label>}
                  <Input
                    id={attrs.name}
                    name={attrs.name}
                    type={attrs.type}
                    defaultValue={attrs.value as string}
                    required={attrs.required}
                    pattern={attrs.pattern}
                  />
                </div>
              )
          })}
        </form>

        <Separator className="my-4" />

        <p className="text-muted-foreground text-sm text-center">
          Já tem uma conta?
          <Link href="/login" className="ml-1 text-foreground font-semibold hover:underline">
            Entrar
          </Link>
        </p>
      </CardContent>
    </Card>
  )
}