const translations: Record<string, string> = {
  "ID": "Email ou Username",
  "Password": "Senha",
  "Sign in with password": "Entrar",
  "Sign up": "Cadastrar",
  "E-Mail": "E-mail",
  "Username": "Nome de usuário",
  "Sign up with password": "Cadastrar",
}

export function getTranslate(text: string | undefined) {
  return text ? (translations[text] || text) : undefined
}