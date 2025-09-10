import { useState } from 'react'
import { evaluatePolicy } from '../api/underwritingApi'
import toast from 'react-hot-toast'
export default function PolicyWizard(){
  const [step,setStep] = useState(1)
  const [payload,setPayload] = useState({ policyId: 'P-'+Date.now(), riskFactors: { age:'', smoker:false, sumInsured:'' }})
  const [loading,setLoading] = useState(false)
  const [result,setResult] = useState(null)

  const next = ()=> setStep(s=>Math.min(3,s+1))
  const prev = ()=> setStep(s=>Math.max(1,s-1))

  const submit = async ()=>{
    setLoading(true)
    try{
      const body = { policyId: payload.policyId, riskFactors: { age: Number(payload.riskFactors.age), smoker: payload.riskFactors.smoker, sumInsured: Number(payload.riskFactors.sumInsured) } }
      const res = await evaluatePolicy(body)
      setResult(res)
      toast.success('Evaluation complete')
      setStep(3)
    }catch(e){
      toast.error('Evaluation failed')
    }finally{ setLoading(false) }
  }

  return (
    <div className="bg-white p-6 rounded shadow">
      <div className="flex items-center justify-between mb-4">
        <div className="text-lg font-semibold">Policy Evaluation</div>
        <div className="text-sm text-gray-500">Step {step} / 3</div>
      </div>
      {step===1 && (
        <div>
          <label className="block text-sm">Policy ID</label>
          <input className="mt-1 w-full border p-2 rounded" value={payload.policyId} onChange={e=>setPayload({...payload, policyId: e.target.value})} />
          <div className="mt-4 flex justify-end">
            <button className="bg-blue-600 text-white px-4 py-2 rounded" onClick={next}>Next</button>
          </div>
        </div>
      )}
      {step===2 && (
        <div>
          <label className="block text-sm">Age</label>
          <input className="mt-1 w-full border p-2 rounded" value={payload.riskFactors.age} onChange={e=>setPayload({...payload, riskFactors:{...payload.riskFactors, age: e.target.value}})} />
          <label className="block text-sm mt-3">Sum Insured</label>
          <input className="mt-1 w-full border p-2 rounded" value={payload.riskFactors.sumInsured} onChange={e=>setPayload({...payload, riskFactors:{...payload.riskFactors, sumInsured: e.target.value}})} />
          <div className="flex items-center gap-3 mt-3">
            <input id="sm" type="checkbox" checked={payload.riskFactors.smoker} onChange={e=>setPayload({...payload, riskFactors:{...payload.riskFactors, smoker: e.target.checked}})} />
            <label htmlFor="sm">Smoker</label>
          </div>
          <div className="mt-4 flex justify-between">
            <button className="px-4 py-2 border rounded" onClick={prev}>Back</button>
            <button className="bg-blue-600 text-white px-4 py-2 rounded" onClick={submit} disabled={loading}>{loading? 'Evaluating...':'Evaluate'}</button>
          </div>
        </div>
      )}
      {step===3 && result && (
        <div>
          <div className="text-sm text-gray-500">Decision</div>
          <div className="text-2xl font-bold">{result.decision}</div>
          <div className="mt-3">Score: <span className="font-semibold">{result.score}</span></div>
          <div className="mt-4">
            <button className="px-4 py-2 border rounded" onClick={()=>{ setStep(1); setResult(null); setPayload({ policyId: 'P-'+Date.now(), riskFactors:{ age:'', smoker:false, sumInsured:'' }}) }}>Start new</button>
          </div>
        </div>
      )}
    </div>
  )
}
