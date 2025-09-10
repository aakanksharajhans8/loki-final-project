import { useEffect, useState } from 'react';
import { listRules, createRule } from '../api/underwritingApi';

export default function Rules(){
  const [rules, setRules] = useState([]);
  const [form, setForm] = useState({name:'', condition:"", weight:10});
  const [loading, setLoading] = useState(false);

  useEffect(()=>{ (async ()=>{ setLoading(true); try{ setRules(await listRules()) }catch(e){} finally{ setLoading(false) } })() },[])

  const submit = async (e)=>{
    e.preventDefault();
    try{
      const created = await createRule(form);
      setRules(prev=>[...prev, created]);
      setForm({name:'', condition:'', weight:10});
    }catch(e){ console.error(e) }
  }

  return (
    <div className="grid md:grid-cols-2 gap-6">
      <div>
        <div className="bg-white p-4 rounded shadow">
          <h3 className="font-semibold">Create Rule</h3>
          <form className="mt-3 space-y-3" onSubmit={submit}>
            <input placeholder="Name" value={form.name} onChange={e=>setForm({...form,name:e.target.value})} className="w-full border p-2 rounded" />
            <input placeholder="Condition e.g. #customer['age']>60" value={form.condition} onChange={e=>setForm({...form,condition:e.target.value})} className="w-full border p-2 rounded" />
            <input type="number" value={form.weight} onChange={e=>setForm({...form,weight:e.target.value})} className="w-full border p-2 rounded" />
            <div><button className="bg-green-600 text-white px-4 py-2 rounded">Create</button></div>
          </form>
        </div>
      </div>
      <div>
        <div className="bg-white p-4 rounded shadow">
          <h3 className="font-semibold">Rules</h3>
          <div className="mt-3 space-y-2">
            {rules.map(r=>(
              <div key={r.id} className="border p-2 rounded">
                <div className="font-medium">{r.name}</div>
                <div className="text-sm text-gray-600">{r.condition}</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
