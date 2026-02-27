import { redirect } from "next/navigation"
import { hydraAdmin } from "@/lib/hydra"

export default async function ConsentPage({
  searchParams,
}: {
  searchParams: Promise<{ consent_challenge?: string }>
}) {
  const params = await searchParams
  const challenge = params.consent_challenge

  if (!challenge) {
    redirect("/")
  }

  // Fetch the consent request from Hydra
  const { data: consentRequest } = await hydraAdmin.getOAuth2ConsentRequest({
    consentChallenge: challenge,
  })

  // Auto-accept consent for our first-party application
  const { data: completedRequest } = await hydraAdmin.acceptOAuth2ConsentRequest({
    consentChallenge: challenge,
    acceptOAuth2ConsentRequest: {
      grant_scope: consentRequest.requested_scope,
      grant_access_token_audience: consentRequest.requested_access_token_audience,
    },
  })

  redirect(completedRequest.redirect_to)
}
