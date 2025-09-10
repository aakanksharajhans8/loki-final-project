import { PieChart, Pie, Cell, ResponsiveContainer } from 'recharts'
export default function ChartArea({data}){
  const COLORS = ['#4f46e5','#10b981','#ef4444','#f59e0b']
  return (
    <div className="bg-white p-4 rounded shadow">
      <div className="text-sm text-gray-500">Decisions Breakdown</div>
      <div style={{width:'100%', height:220}} className="mt-3">
        <ResponsiveContainer>
          <PieChart>
            <Pie data={data} dataKey="value" nameKey="name" innerRadius={40} outerRadius={80} label>
              {data.map((entry, idx)=><Cell key={idx} fill={COLORS[idx % COLORS.length]} />)}
            </Pie>
          </Pie>
        </ResponsiveContainer>
      </div>
    </div>
  )
}
