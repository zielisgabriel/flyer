import { redirect } from "next/navigation"
import { LoginForm } from "@/components/login-form"

export default async function LoginPage({
  searchParams
}: {
  searchParams: Promise<{ flow?: string; login_challenge?: string }>
}) {
  const params = await searchParams

  // When Hydra redirects here with a login_challenge,
  // forward it to Kratos so it creates a flow linked to Hydra's OAuth2 session
  if (params.login_challenge && !params.flow) {
    redirect(
      `http://localhost:4433/self-service/login/browser?login_challenge=${params.login_challenge}`
    )
  }

  return (
    <main className="flex items-center justify-center h-screen">
      <LoginForm />
    </main>
  )
}