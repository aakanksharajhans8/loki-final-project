import { AreaChart, Area, ResponsiveContainer } from 'recharts'
export default function MiniChart({data, color}){
  return (
    <div style={{width:'100%', height:60}}>
      <ResponsiveContainer>
        <AreaChart data={data}>
          <Area type="monotone" dataKey="value" stroke={color} fill={color} fillOpacity={0.12}/>
        </AreaChart>
      </ResponsiveContainer>
    </div>
  )
}
