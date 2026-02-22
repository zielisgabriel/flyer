"use client"

import { ory } from "@/lib/ory"
import { LoginFlow, UiNodeInputAttributes } from "@ory/client"
import { redirect, useSearchParams } from "next/navigation"
import { useEffect, useState } from "react"
import { Input } from "./ui/input"
import { Label } from "./ui/label"
import { Button } from "./ui/button"
import { Spinner } from "./ui/spinner"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import Link from "next/link"
import { Separator } from "./ui/separator"
import { getTranslate } from "@/utils/auth-form-translate"

export function LoginForm() {
  const searchParams = useSearchParams()
  const flowId = searchParams.get("flow")
  const [flow, setFlow] = useState<LoginFlow | null>(null)

  useEffect(() => {
    console.log(flowId)

    if (flowId) {
      ory.getLoginFlow({ id: String(flowId) })
        .then(({ data }) => {
          console.log(data)
          setFlow(data)
        })
        .catch((err) => console.error("Erro ao buscar o fluxo:", err))
    } else {
      redirect("http://localhost:4433/self-service/login/browser")
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
          Entrar
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
                  {node.meta.label && (
                    <Label htmlFor={attrs.name}>
                       {getTranslate(node.meta.label.text)}
                    </Label>
                  )}
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
          Ainda não tem uma conta?
          <Link href={"/register"} className="ml-1 text-foreground font-semibold hover:underline">
            Cadastra-se
          </Link>
        </p>
      </CardContent>
    </Card>
  )
}