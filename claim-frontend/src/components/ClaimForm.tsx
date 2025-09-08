import { useState } from "react"
import {
  submitClaim,
  type ClaimForm as ClaimFormType,
  type ClaimResponse,
} from "../api/claims"

export default function ClaimForm() {
  const [form, setForm] = useState<ClaimFormType>({
    policyId: "",
    name: "",
    phone: "",
    email: "",
    policyType: "Health",
    amountInvested: 0,
    documentType: "",
    documentFile: null, 
  })
  const [loading, setLoading] = useState(false)
  const [result, setResult] = useState<ClaimResponse | null>(null)
  const [error, setError] = useState("")

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target
    setForm((prev) => ({
      ...prev,
      [name]: name === "amountInvested" ? Number(value) : value,
    }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError("")
    setResult(null)

    const response = await submitClaim(form)
    setLoading(false)

    if (!response.valid) {
      setError(response.message || "❌ No record available for this policy ID")
    } else {
      setResult(response)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4 py-12">
      <div className="max-w-3xl w-full bg-white shadow-xl rounded-2xl overflow-hidden">
        
        <div className="bg-gradient-to-r from-blue-600 to-indigo-600 py-8 text-center">
          <h2 className="text-3xl font-bold text-white">
            Insurance Claim Settlement
          </h2>
          <p className="text-blue-100 mt-2 text-sm">
            Submit your claim details to process settlement
          </p>
        </div>

        
        <form onSubmit={handleSubmit} className="p-8 space-y-8">
          
          <div>
            <h3 className="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Claim Information
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <input
                type="text"
                name="policyId"
                placeholder="Policy ID"
                value={form.policyId}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
                required
              />
              <input
                type="text"
                name="name"
                placeholder="Full Name"
                value={form.name}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
                required
              />
              <input
                type="tel"
                name="phone"
                placeholder="Phone Number"
                value={form.phone}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
                required
              />
              <input
                type="email"
                name="email"
                placeholder="Email Address"
                value={form.email}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
                required
              />
              <select
                name="policyType"
                value={form.policyType}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
              >
                <option value="Health">Health Insurance</option>
                <option value="Motor">Motor Insurance</option>
                <option value="Life">Life Insurance</option>
              </select>
              <input
                type="number"
                name="amountInvested"
                placeholder="Amount Invested"
                value={form.amountInvested === 0 ? "" : form.amountInvested}
                onChange={handleChange}
                className="block w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
                required
              />
            </div>
          </div>

          
          <div className="flex justify-center">
            <button
              type="submit"
              disabled={loading}
              className="bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white text-lg font-semibold rounded-lg px-10 py-3 shadow-lg transition disabled:opacity-50"
            >
              {loading ? "Processing Claim..." : "Submit Claim"}
            </button>
          </div>
        </form>

        
        {loading && (
          <div className="mt-6 flex justify-center">
            <div className="w-10 h-10 border-4 border-blue-400 border-t-transparent rounded-full animate-spin"></div>
          </div>
        )}

       
        {error && (
          <div className="mx-8 my-6 text-center text-red-600 font-semibold bg-red-50 py-3 rounded-lg border border-red-200">
            {error}
          </div>
        )}

        
        {result && (
          <div className="m-8 bg-green-50 p-6 rounded-xl shadow-inner border border-green-200">
            <h3 className="text-xl font-bold text-green-700 mb-4">
              ✅ Claim Settled Successfully
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-gray-800">
              <p>
                <strong>Policy ID:</strong> {result.details?.policyId}
              </p>
              <p>
                <strong>Name:</strong> {result.details?.name}
              </p>
              <p>
                <strong>Policy Type:</strong> {result.details?.policyType}
              </p>
              <p>
                <strong>Amount Invested:</strong> ₹{result.details?.amountInvested}
              </p>
              <p className="text-xl font-bold text-green-700 col-span-2">
                Final Settled Amount: ₹{result.settledAmount}
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
