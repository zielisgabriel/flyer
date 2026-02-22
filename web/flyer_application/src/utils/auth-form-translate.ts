const translations: Record<string, string> = {
  "ID": "Email ou Username",
  "Password": "Senha",
  "Sign in with password": "Entrar com senha",
  "Sign up": "Cadastrar"
}

export function getTranslate(text: string | undefined) {
  return text ? (translations[text] || text) : undefined
}