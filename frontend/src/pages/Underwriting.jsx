import PolicyWizard from '../components/PolicyWizard'
export default function Underwriting(){
  return (
    <div className="grid md:grid-cols-2 gap-6">
      <PolicyWizard />
      <div className="bg-white p-6 rounded shadow">
        <h3 className="font-semibold">Tips</h3>
        <ul className="list-disc pl-5 mt-2 text-sm text-gray-600">
          <li>Use rules to flag high risk</li>
          <li>Refer complex cases</li>
          <li>Audit decisions regularly</li>
        </ul>
      </div>
    </div>
  )
}
