"use client"

import { ory } from "@/lib/ory"
import { LoginFlow, UiNodeInputAttributes } from "@ory/client"
import { redirect, useSearchParams } from "next/navigation"
import { useEffect, useState } from "react"
import { Input } from "./ui/input"
import { Label } from "./ui/label"
import { Button } from "./ui/button"
import { Spinner } from "./ui/spinner"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./ui/card"
import Link from "next/link"
import { Separator } from "./ui/separator"
import { getTranslate } from "@/utils/auth-form-translate"
import { AlertCircle } from "lucide-react"

export function LoginForm() {
  const searchParams = useSearchParams()
  const flowId = searchParams.get("flow")
  const [flow, setFlow] = useState<LoginFlow | null>(null)

  useEffect(() => {
    if (flowId) {
      ory
        .getLoginFlow({ id: String(flowId) })
        .then(({ data }) => setFlow(data))
        .catch((err) => console.error("Erro ao buscar o fluxo:", err))
    } else {
      redirect("http://localhost:4433/self-service/login/browser")
    }
  }, [flowId])

  if (!flow)
    return (
      <Card className="w-full border-border/50 bg-card/80 backdrop-blur-xl shadow-2xl">
        <CardContent className="flex items-center justify-center py-20">
          <Spinner className="size-6 text-muted-foreground" />
        </CardContent>
      </Card>
    )

  return (
    <Card className="w-full border-border/50 bg-card/80 backdrop-blur-xl shadow-2xl">
      <CardHeader className="items-center text-center pb-2">
        <div className="mb-2 flex size-11 items-center justify-center rounded-lg bg-primary text-primary-foreground">
          <span className="text-base font-bold tracking-tight">F</span>
        </div>
        <CardTitle className="text-2xl font-bold tracking-tight">
          Bem-vindo de volta
        </CardTitle>
        <CardDescription>
          Entre com suas credenciais para continuar
        </CardDescription>
      </CardHeader>

      <CardContent className="pt-4">
        {flow.ui.messages && flow.ui.messages.length > 0 && (
          <div className="mb-4 flex items-start gap-2 rounded-lg bg-destructive/10 border border-destructive/20 p-3">
            <AlertCircle className="size-4 mt-0.5 text-destructive shrink-0" />
            <div className="space-y-1">
              {flow.ui.messages.map((msg) => (
                <p key={msg.id} className="text-sm text-destructive">
                  {msg.text}
                </p>
              ))}
            </div>
          </div>
        )}

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
                  <div key={attrs.name} className="pt-2">
                    <Button
                      className="w-full h-10 font-semibold"
                      type="submit"
                      name={attrs.name}
                      value={attrs.value}
                    >
                      {getTranslate(node.meta.label?.text)}
                    </Button>
                  </div>
                )
              }

              const translatedLabel = getTranslate(node.meta.label?.text)

              return (
                <div key={attrs.name} className="space-y-2">
                  {translatedLabel && (
                    <Label htmlFor={attrs.name} className="text-sm font-medium">
                      {translatedLabel}
                    </Label>
                  )}
                  <Input
                    id={attrs.name}
                    name={attrs.name}
                    type={attrs.type}
                    defaultValue={attrs.value as string}
                    required={attrs.required}
                    pattern={attrs.pattern}
                    placeholder={translatedLabel}
                    className="h-10"
                  />
                  {node.messages.length > 0 && (
                    <div className="space-y-1">
                      {node.messages.map((msg) => (
                        <p key={msg.id} className="text-xs text-destructive">
                          {msg.text}
                        </p>
                      ))}
                    </div>
                  )}
                </div>
              )
            })}
        </form>
      </CardContent>

      <CardFooter className="flex-col gap-4 pt-2">
        <Separator />
        <p className="text-sm text-muted-foreground">
          Ainda não tem uma conta?{" "}
          <Link
            href="/register"
            className="text-foreground font-semibold hover:underline"
          >
            Cadastre-se
          </Link>
        </p>
      </CardFooter>
    </Card>
  )
}