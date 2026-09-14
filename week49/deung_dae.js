// 일단 ㄱㄱ
// 뱃길의 양쪽 끝 둥대 중 '적어도' 하나는 켜져 있어야 함.
// 특정 노드를 집어서 연결된 노드로 이동하며 등대를 놨다 안 놨다 하는 경우,
// 이전 등대가 켜져 있다면 다음 등대는 킨다, 끈다 두가지 가능
// 이전 등대가 꺼져 있다면 다음 등대를 무조건 켜야함.
// 완탐 시 2^100,000
// 줄여야 함
// 중복되는 경우가 보임
// 문제의 예시로 설명
// 5번 등대를 골라서 연결된 등대를 이동하며 켰다 끈다를 처리할 때, 1번쪽을 보겠음.
// 일단 5번을 끈다(0) -> 1번(1) ->
// 5번을 킨다(1) -> 1번(1) -> (위에랑 경우가 겹침)
//              -> 0번(1) ->
// 하위 경우의 수가 겹치는 경우 DP를 사용할 수 있음. 이렇게 진행하면 시간 복잡도는 O(N)
// 저장하는 값은 현재 상태의 최솟값으로 상태를 나누는 기준은 등대 번호와 현재 등대(노드)의 점등 여부임.
// 해당 상태일 때, 하위(이동해온 등대를 제외한 나머지 등대) 등대를 조건에 맞게 최소 키는 개수를 저장.
// DP[등대 번호][점등 여부] = 점등된 하위 등대의 최소 갯수
// 탑다운 말고 바텀업 ㄱㄱ -> ㄴㄴㄴ 탑다운이 편함. dfs + memo
// 1번을 루트로 하는 트리를 생각하고 리프노드들 부터 위로 올라가며 DP 배열 채우기
// DP[부모노드][점등] = MIN(DP[자식노드][점등], DP[자식노드][소등]) 들의 합
// DP[부모노드][소등] = DP[자식노드][점등] 들의 합
// 탑다운 1케이스 실패 런타임에러임. stack overflow일수도 있어서 바텀업 해보자

// 바텀업
function solution(n, lighthouse) {
  let edges = [...Array(n + 1)].map(() => []);
  lighthouse.forEach((line, i) => {
    let [a, b] = line;
    edges[a].push(b);
    edges[b].push(a);
  });

  const parent = Array(n + 1).fill(0);

  // 스택을 사용해서 노드 순서 저장, 큐는 js에서 pop 연산이 o(1)이 아니라 연산수가 늘어날 수 있음.
  let stack = [1];
  let order = [];
  while (stack.length > 0) {
    let cur = stack.pop();
    order.push(cur);

    for (let child of edges[cur]) {
      if (child === parent[cur]) continue;
      parent[child] = cur;
      stack.push(child);
    }
  }

  // order 반대로 실행하며 점화식으로 채우기
  let dp = [...Array(n + 1)].map(() => [0, 0]);
  for (let i = order.length - 1; i > -1; i--) {
    let node = order[i];

    dp[node][0] = 0;
    dp[node][1] = 1;
    for (let child of edges[node]) {
      if (child === parent[node]) continue;

      dp[node][0] += dp[child][1];
      dp[node][1] += Math.min(dp[child][1], dp[child][0]);
    }
  }

  return Math.min(...dp[1]);
}

// // 탑다운
// function solution(n, lighthouse) {

//     let edges = [...Array(n+1)].map(()=>[]);
//     lighthouse.forEach((line, i)=>{
//         let [a, b] = line;
//         edges[a].push(b);
//         edges[b].push(a);
//     })

//     const memo = [...Array(n+1)].map(()=>Array(2).fill(Infinity)); //

//     // 부모가 어떤 노드인지 알고 있어야함.
//     const dfs = (node, parent=0) => {
//         if (memo[node][0] !== Infinity) return;

//         memo[node][0] = 0;
//         memo[node][1] = 1;
//         for (let child of edges[node]) {
//             if (child === parent) continue;

//             dfs(child, node);

//             memo[node][0] += memo[child][1];
//             memo[node][1] += Math.min(memo[child][0], memo[child][1]);
//         }

//         return;
//     }

//     dfs(1);

//     return Math.min(...memo[1]);
// }
