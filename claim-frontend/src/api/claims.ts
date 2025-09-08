export type ClaimForm = {
  policyId: string
  name: string
  phone: string
  email: string
  policyType: "Health" | "Motor" | "Life"
  amountInvested: number
  documentType: string
  documentFile?: File | null // optional file
}

export type ClaimResponse = {
  valid: boolean
  message?: string
  settledAmount?: number
  details?: any
}

export async function submitClaim(form: ClaimForm): Promise<ClaimResponse> {
  try {
    const formData = new FormData()
    formData.append("policyId", form.policyId)
    formData.append("name", form.name)
    formData.append("phone", form.phone)
    formData.append("email", form.email)
    formData.append("policyType", form.policyType)
    formData.append("amountInvested", form.amountInvested.toString())
    formData.append("documentType", form.documentType)

    if (form.documentFile) {
      formData.append("documentFile", form.documentFile)
    }

    const response = await fetch("http://localhost:8080/api/claims/settle", {
      method: "POST",
      body: formData, // <-- automatically sets multipart/form-data
    })

    if (!response.ok) {
      return { valid: false, message: "❌ Failed to process claim" }
    }

    return await response.json()
  } catch (error) {
    console.error("Error submitting claim:", error)
    return { valid: false, message: "⚠️ Network error. Please try again." }
  }
}
