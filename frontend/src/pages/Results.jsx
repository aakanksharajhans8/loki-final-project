import { useEffect, useState } from 'react';
import { listDecisions } from '../api/underwritingApi';
export default function Results(){
  const [decisions, setDecisions] = useState([]);
  useEffect(()=>{ (async ()=>{ try{ setDecisions(await listDecisions()) }catch(e){} })() },[])
  return (
    <div>
      <div className="bg-white p-4 rounded shadow">
        <h3 className="font-semibold">Decision History</h3>
        <div className="mt-3 overflow-x-auto">
          <table className="w-full">
            <thead className="text-left text-sm text-gray-500">
              <tr><th className="p-2">Policy</th><th className="p-2">Decision</th><th className="p-2">Score</th><th className="p-2">When</th></tr>
            </thead>
            <tbody>
              {decisions.map(d=>(
                <tr key={d.id} className="border-t">
                  <td className="p-2">{d.policyId}</td>
                  <td className="p-2">{d.decision}</td>
                  <td className="p-2">{d.score}</td>
                  <td className="p-2">{new Date(d.evaluatedAt || d.evaluated_at || Date.now()).toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
