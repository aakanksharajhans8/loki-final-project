export default function KpiCard({title, value, delta}){
  return (
    <div className="bg-white p-4 rounded-lg shadow">
      <div className="text-sm text-gray-500">{title}</div>
      <div className="mt-2 flex items-baseline gap-3">
        <div className="text-2xl font-bold">{value}</div>
        {delta && <div className="text-sm text-green-600">{delta}</div>}
      </div>
    </div>
  )
}
