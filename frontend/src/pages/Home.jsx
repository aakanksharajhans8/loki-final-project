import KpiCard from '../components/KpiCard'
import MiniChart from '../components/MiniChart'
import ChartArea from '../components/ChartArea'
export default function Home(){
  const kpis = [
    { title:'Policies Evaluated', value: 1240, delta:'+8%'},
    { title:'Approval Rate', value: '72%', delta:'+2%'},
    { title:'Avg Score', value: 67, delta:'-1%'}
  ]
  const miniData = [{value:10},{value:20},{value:15},{value:30},{value:25}]
  const pieData = [{name:'APPROVED', value: 72},{name:'REFERRED', value:30},{name:'DECLINED', value:18}]
  return (
    <div className="space-y-6">
      <div className="grid md:grid-cols-3 gap-4">
        {kpis.map((k,i)=>(<KpiCard key={i} title={k.title} value={k.value} delta={k.delta}><MiniChart data={miniData} color="#4f46e5"/></KpiCard>))}
      </div>
      <div className="grid md:grid-cols-3 gap-4">
        <div className="md:col-span-2">
          <ChartArea data={pieData} />
        </div>
        <div>
          <div className="bg-white p-4 rounded shadow">
            <div className="text-sm text-gray-500">Quick Actions</div>
            <div className="mt-3 space-y-2">
              <button className="w-full bg-blue-600 text-white px-4 py-2 rounded">Evaluate Policy</button>
              <button className="w-full border px-4 py-2 rounded">Manage Rules</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
