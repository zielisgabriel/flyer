import NextAuth from "next-auth"
import OryHydra from "next-auth/providers/ory-hydra"
 
export const { handlers, signIn, signOut, auth } = NextAuth({
  trustHost: true,
  pages: {
    signIn: "/login",
  },
  providers: [
    OryHydra({
      clientId: process.env.ORY_HYDRA_CLIENT_ID,
      clientSecret: process.env.ORY_HYDRA_CLIENT_SECRET,
      issuer: process.env.ORY_HYDRA_ISSUER,
    })
  ],
})