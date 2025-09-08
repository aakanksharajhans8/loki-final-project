import { useEffect, useState } from "react"

type Claim = {
  policyId: string
  name: string
  phone: string
  email: string
  documentType: string
  policyType: string
  amountInvested: number
  status: "Settled" | "Unsettled"
}

export default function SettledClaims() {
  const [claims, setClaims] = useState<Claim[]>([])
  const [loading, setLoading] = useState(true)
  const [search, setSearch] = useState("")

  useEffect(() => {
    fetch("http://localhost:5000/api/claims/settled")
      .then((res) => res.json())
      .then((data) => {
        setClaims(data)
        setLoading(false)
      })
      .catch((err) => {
        console.error("Error fetching claims:", err)
        setLoading(false)
      })
  }, [])

  
  const filteredClaims = claims.filter(
    (c) =>
      c.policyId.toLowerCase().includes(search.toLowerCase()) ||
      c.name.toLowerCase().includes(search.toLowerCase()) ||
      c.policyType.toLowerCase().includes(search.toLowerCase())
  )

  return (
    <div className="min-h-screen bg-gray-50 pt-24 px-6">
      <div className="max-w-7xl mx-auto bg-white shadow-xl rounded-2xl overflow-hidden">
       
        <div className="bg-gradient-to-r from-indigo-600 to-purple-600 px-6 py-6 flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
          <h1 className="text-2xl font-bold text-white">Settled Claims</h1>
          <div className="flex gap-3 w-full md:w-auto">
            <input
              type="text"
              placeholder="Search by ID, name, or type..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="flex-1 md:w-64 px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
            />
            <button className="px-4 py-2 rounded-lg bg-white/20 hover:bg-white/30 text-white transition">
              ⚙️
            </button>
          </div>
        </div>

        
        <div className="p-6">
          {loading ? (
            <p className="text-gray-500">Loading claims...</p>
          ) : filteredClaims.length === 0 ? (
            <p className="text-gray-500 text-center py-10">
              No settled claims found.
            </p>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full border-collapse">
                <thead>
                  <tr className="bg-gray-100 text-left text-sm font-semibold text-gray-600">
                    <th className="p-3">Policy ID</th>
                    <th className="p-3">Name</th>
                    <th className="p-3">Phone</th>
                    <th className="p-3">Email</th>
                    <th className="p-3">Document</th>
                    <th className="p-3">Policy Type</th>
                    <th className="p-3">Amount</th>
                    <th className="p-3">Status</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredClaims.map((claim, idx) => (
                    <tr
                      key={idx}
                      className="border-b hover:bg-gray-50 transition-colors"
                    >
                      <td className="p-3 text-sm">{claim.policyId}</td>
                      <td className="p-3 text-sm">{claim.name}</td>
                      <td className="p-3 text-sm">{claim.phone}</td>
                      <td className="p-3 text-sm">{claim.email}</td>
                      <td className="p-3 text-sm">{claim.documentType}</td>
                      <td className="p-3 text-sm">{claim.policyType}</td>
                      <td className="p-3 text-sm font-medium">
                        ₹{claim.amountInvested}
                      </td>
                      <td
                        className={`p-3 text-sm font-semibold ${
                          claim.status === "Settled"
                            ? "text-green-600"
                            : "text-red-500"
                        }`}
                      >
                        {claim.status}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
