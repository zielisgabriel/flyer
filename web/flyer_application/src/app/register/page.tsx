import { RegisterForm } from "@/components/register-form"
import { AnimatedGridPattern } from "@/components/ui/animated-grid-pattern"

export default function RegisterPage() {
  return (
    <main className="relative flex items-center justify-center min-h-screen overflow-hidden">
      <AnimatedGridPattern
        className="absolute inset-0 z-0 opacity-40 [mask-image:radial-gradient(600px_circle_at_center,white,transparent)]"
        numSquares={30}
        maxOpacity={0.3}
        duration={3}
        strokeDasharray={2}
      />
      <div className="relative z-10 w-full max-w-[420px] px-4">
        <RegisterForm />
      </div>
    </main>
  )
}